package com.lanina.wino;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Embeddable
public class ImageId implements Serializable {
    String folder;
    Integer counter;
}
