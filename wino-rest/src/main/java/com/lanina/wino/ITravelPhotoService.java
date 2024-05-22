package com.lanina.wino;

import org.javatuples.Pair;
import org.springframework.web.client.HttpClientErrorException;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface ITravelPhotoService {
    List<MetaLocation> getLocations();

    Long countLocations();

    List<MetaLocation> getLocations(int page, int size) throws HttpClientErrorException.NotAcceptable;

    MetaLocation getLocationById(String id);

    MetaImage getImageById(String location, Integer id);

    Pair<Long, Integer> imagesStat();
}
