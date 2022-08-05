package site.golets.pomanager.service;

import org.apache.maven.model.Model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface PomReaderService {

    Optional<Model> readPomModel(String source);

    void updateProperty(Path pomSource, String propertyName, String value);

    List<Model> scan(String rootSource);

}
