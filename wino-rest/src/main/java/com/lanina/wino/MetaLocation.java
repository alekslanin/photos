package com.lanina.wino;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="location")
public class MetaLocation {
    @Id
    private String id;
    private String dir;
    private Date date;
    private int total;
    @Lob
    private byte[] photo;

    public int addPhoto() {
        return ++total;
    }
}
