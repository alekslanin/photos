package com.lanina.wino;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.NotAcceptableStatusException;

@Service
@Slf4j
public class TravelPhotoService {
    final private byte[] imageNotFound;
    private final List<ProtoImage> errorQueue = new ArrayList<>();
    private final List<ProtoImage> images = new ArrayList<>();
    private final WinoMetaImageRepository metaImageRepository;
    private final WinoMetaLocationRepository metaLocationRepository;

    public TravelPhotoService(@Autowired WinoMetaLocationRepository metaLocationRepository,
                              @Autowired WinoMetaImageRepository metaImageRepository,
                              @Value("${wino.photo.location}") String folderLocation,
                              @Value("${wino.preload:false}") Boolean preload) throws IOException {
        imageNotFound = Files.readAllBytes(new ClassPathResource("images/404.jpg").getFile().toPath());
        this.metaImageRepository = metaImageRepository;
        this.metaLocationRepository = metaLocationRepository;
        log.info("loading photos from " + folderLocation);

        load(folderLocation, preload);
        log.info("REPOSITORY has " + metaLocationRepository.count() + " locations.");
    }

    public List<MetaLocation> getLocations() {
        return metaLocationRepository.findAll();
    }

    public List<MetaLocation> getLocations(int page, int size) throws HttpClientErrorException.NotAcceptable {
        var skip = (long) (page - 1) * size;
        if(skip >= metaLocationRepository.count()) {
            throw new NotAcceptableStatusException("page not found :: out of range");
        }

        // 0 1 2 3 4 5 6 7 8 9 10 11
        // page 3 size 2 => (skip) 2 * 2 => (take) 2 :: 4, 5
        // page 1 size 2 => (skip) 0 * 2 => (take) 2 :: 0, 1

        return metaLocationRepository.findAll().stream().skip(skip).limit(size).toList();
    }

    public MetaLocation getLocationById(String id) {
        return metaLocationRepository.getLocationById(id);
    }

    public MetaImage getImageById(String location, Integer id) {
        var proto = images.stream().filter(x -> x.location.equals(location) && Objects.equals(x.number, id)).findFirst();
        if (proto.isPresent()) {
            byte[] bytes = getImage(proto.get().path);
            if (bytes != null) {
                return new MetaImage(new ImageId(location, id), proto.get().folder, bytes);
            }
        }
        return new MetaImage(new ImageId(location, id), null, imageNotFound);
    }

    public Pair<Long, Integer> imagesStat() {
        return new Pair<>(metaImageRepository.count(), errorQueue.size());
    }

    private void load(String folderLocation, boolean preload) {
        log.info("about to load movies from this location: " + folderLocation);
        Path path = Path.of(folderLocation);
        log.info(path + " : " + path.getClass());

        var map = new HashMap<String, MetaLocation>();
        var imagesToLoad = new ArrayList<ProtoImage>();

        try (Stream<Path> filePathStream = Files.walk(path)) {
            filePathStream
                    .parallel()
                    .filter(x -> Files.isRegularFile(x))
                    .filter(x -> x.toString().toLowerCase().endsWith(".jpg"))
                    .forEach(x -> {
                        var splitter = "\\\\";
                        var locationName = Arrays.stream(x.getParent().toString().split(splitter)).reduce((first, second) -> second).orElse(null);
                        var folder = x.getParent().toString();
                        log.debug("Directory : " + locationName + " : " + x.getFileName());

                        if( !map.containsKey(locationName)) {
                            var img = getImage(x);
                            if(img != null) {
                                var value = new MetaLocation(locationName, folder, Date.from(Instant.now()), 0, img);
                                map.put(locationName, value);
                            } else {
                                errorQueue.add(new ProtoImage(locationName, folder, x, -1)); // TODO
                            }
                        }

                        if(map.containsKey(locationName)) {
                            var id = map.get(locationName).addPhoto();
                            imagesToLoad.add(new ProtoImage(locationName, folder, x, id));
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        metaLocationRepository.saveAll( map.values());

        if(!preload) {
            images.addAll(imagesToLoad);
            return;
        }

        CompletableFuture.runAsync(() ->
            imagesToLoad.stream()
                    .parallel()
                    .forEach(x -> {
                        var img = getImage(x.getPath());
                        if (img == null) errorQueue.add(x);
                        else images.add(x);
                    })
        ).join();

        log.info("TOTAL NUMBER OF PROCESSED IMAGES :: " + images.size());
        log.info("TOTAL NUMBER OF FAILED IMAGES :: " + errorQueue.size());
    }

    private byte[] getImage(Path path) {
        log.debug("loading image from folder = " + path);
        var type = Arrays.stream(path.getFileName().toString().split("\\.")).reduce((first, second) -> second).orElse(null);
        if(!"jpg".equalsIgnoreCase(type)) {
            log.error("unknown photo type format ::" + path);
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            var img = ImageIO.read(path.toFile());
            ImageIO.write(img, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("cannot load file :: " + path + " Error: " + e.getMessage());
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    private static class ProtoImage {
        private String location, folder;
        private Path path;
        private Integer number;
    }
}
