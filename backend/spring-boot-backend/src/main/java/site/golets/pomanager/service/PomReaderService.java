package site.golets.pomanager.service;

import org.apache.maven.model.Model;

import java.util.List;
import java.util.Optional;

public interface PomReaderService {

    Optional<Model> readPomModel(String source);

    void writePomModel(Model model);

    List<Model> scan(String rootSource);

}
