package com.lanina.wino;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/*
  TODO: add non blank validations
 */

@Slf4j
@RestController
@RequestMapping("${wino.request-mapping}")
@AllArgsConstructor
@Tag(name = "Images controller - get locations and images")
public class WinoController {

    private TravelPhotoService service;

    @GetMapping(value = "/hello")
    @Operation(summary = "hello end point")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    public record LocationResponse(String title, String image, Integer total, String year, String country, List<String> actors) {}
    public record TableResponse(String year, String title, String country, Integer total, String wineregion) {}

    public record ImageResponse(String folder, String image, Integer id) {}

    @GetMapping(value = "/locations/all", params = { "page", "size" })
    @Operation(summary = "get ALL locations")
    public ResponseEntity<List<LocationResponse>> locations(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("get all images called with page: " + page + " and size: " + size);
        var locations = service.getLocations(page, size);
        var data = locations
                .stream()
                .map(x -> new LocationResponse(x.getId(), "data:image/jpg;base64," + Base64.getEncoder().encodeToString(x.getPhoto()), x.getTotal(), null, null, Collections.emptyList()))
                //.sorted(Comparator.comparing(x -> x.title().toLowerCase()))
                .sorted(Comparator.comparing(x -> yearFromTitle(x.title())))
                .toList();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/years/all")
    @Operation(summary = "get ALL locations grouped by year")
    public ResponseEntity<Map<String, List<String>>> years() {
        var locations = service.getLocations();

        var data = locations
                .stream()
                .map(x -> new LocationResponse(x.getId(), null, x.getTotal(), yearFromTitle(x.getId()), null, Collections.emptyList()))
                .collect(groupingBy(LocationResponse::year, Collectors.mapping(x -> x.title(), toList())));

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/years/table")
    @Operation(summary = "get ALL locations as table data")
    public ResponseEntity<List<TableResponse>> yearsTable() {
        var locations = service.getLocations();

        var data = locations
                .stream()
                .map(x -> new TableResponse(yearFromTitle(x.getId()), x.getId(), "France", x.getTotal(), "Bordo, Barollo"))
                .toList();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private static String yearFromTitle(String title) {
        var year =  Arrays.stream(title.split(" ")).reduce((first, second) -> second).orElse(null);
        return isNumeric(year) ? year : "n/a";
    }

    @GetMapping(value = "/location")
    @Operation(summary = "get location information by name")
    public ResponseEntity<MetaLocation> location(@RequestParam(name="id") String id) {
        var location = service.getLocationById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
    @GetMapping(value = "/image")
    @Operation(summary = "get location image by id")
    public ResponseEntity<ImageResponse> image(@RequestParam(name="location") String location, @RequestParam(name="id") Integer id) {
        var image = service.getImageById(location, id);
        var response = new ImageResponse(image.getName(), "data:image/jpg;base64," + Base64.getEncoder().encodeToString(image.getImage()), image.getId().counter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/stat")
    @Operation(summary = "get total number of images and number of corrupted images")
    public ResponseEntity<String> stat() {
        var stat = service.imagesStat();
        var message = "Number of loaded images :: " + stat.getValue0() + ". Number of corrupted images :: " + stat.getValue1();;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
