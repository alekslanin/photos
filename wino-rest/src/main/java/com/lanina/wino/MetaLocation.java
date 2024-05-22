package com.lanina.wino;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="location")
public class MetaLocation {
    public MetaLocation(String locationName, String folder, byte[] img, MetaFile meta) {
        id = locationName;
        dir = folder;
        date = Date.from(Instant.now());
        total = 0;
        photo = img;

        if(meta != null) {
            if(meta.getCountries() != null) countries = String.join(",", meta.getCountries());
            if(meta.getActors() != null) actors = String.join(",", meta.getActors());
            if(meta.getRegions() != null) regions = String.join(",", meta.getRegions().toString());
            if(meta.getWines() != null) wines = String.join(",", meta.getWines().toString());
        }
    }

    @Id
    private String id;
    private String dir;
    private Date date;
    private int total;
    @Lob
    private byte[] photo;

    @Null
    private String countries;
    @Null
    private String regions;
    @Null
    private String actors;
    @Null
    private String wines;
    @Null
    private String metaYear;

    public int addPhoto() {
        return ++total;
    }

    public String getYearAsString() {
        if(getMetaYear() != null) return getMetaYear();
        return yearFromTitle(getId());
    }

    private static String yearFromTitle(String title) {
        var year =  Arrays.stream(title.split(" ")).reduce((first, second) -> second).orElse(null);
        return isNumeric(year) ? year : "n/a";
    }
}
