package site.golets.pomanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Service;
import site.golets.pomanager.service.PomReaderService;
import site.golets.pomanager.utils.PathUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileSystemPomReaderServiceImpl implements PomReaderService {

    private final MavenXpp3Reader pomReader = new MavenXpp3Reader();

    public Optional<Model> readPomModel(String path) {
        try (Stream<Path> pathStream = Files.find(Paths.get(path), 1, (p, a) -> p.endsWith("pom.xml"))) {
            Optional<Path> pomPath = pathStream.findFirst();
            return pomPath.flatMap(this::pathToModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writePomModel(Model model) {
        throw new UnsupportedOperationException("TODO");
    }

    private Optional<Model> pathToModel(Path pomPath) {
        Model model = null;
        try {
            log.info("Parsing pom: {}", pomPath);
            model = pomReader.read(new FileReader(pomPath.toFile()));
        } catch (IOException | XmlPullParserException e) {
            log.error("An error occurred while parsing pom file: {}", pomPath, e);
        }
        return Optional.ofNullable(model);
    }

    public List<Model> scan(String rootPath) {
        PathUtils.existsFailFast(rootPath);

        File root = Paths.get(rootPath).toFile();

        if (root.isFile()) {
            log.info("Root path {} must be a directory. Parent directory of a file will be used instead.", rootPath);
            root = root.getParentFile();
        }

        Optional<File[]> files = Optional.ofNullable(root.listFiles());
        return Arrays.stream(files.orElse(new File[] {}))
                .map(File::getAbsolutePath)
                .map(this::readPomModel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
