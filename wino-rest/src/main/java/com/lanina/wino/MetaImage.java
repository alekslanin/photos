package com.lanina.wino;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity
@Table(name="photo")
public class MetaImage {
    @EmbeddedId
    private ImageId id;

    private String name;

    @Lob
    private byte[] image;
}
