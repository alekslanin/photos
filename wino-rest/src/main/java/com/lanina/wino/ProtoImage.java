package com.lanina.wino;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

@Data
@AllArgsConstructor
public class ProtoImage {
    public String location, folder;
    public Path path;
    public Integer number;
}