package com.lanina.wino;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class MetaReadTest {

    @Test
    void read() {
        Yaml yaml = new Yaml(new Constructor(MetaFile.class, new LoaderOptions()));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("meta.yml");
        var test = yaml.load(inputStream);
        assertNotNull(test);
        log.info(test.toString());
    }
}
