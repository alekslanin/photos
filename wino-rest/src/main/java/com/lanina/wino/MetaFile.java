package com.lanina.wino;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MetaFile {
    private Integer year;
    private List<String> countries;
    private List<String> regions;
    private List<String> actors;
    private List<String> wines;
}