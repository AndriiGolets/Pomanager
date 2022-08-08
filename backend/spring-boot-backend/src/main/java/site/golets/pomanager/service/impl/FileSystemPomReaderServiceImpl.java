package site.golets.pomanager.service.impl;

import lombok.Data;
import lombok.experimental.Accessors;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class FileSystemPomReaderServiceImpl implements PomReaderService {

    private final MavenXpp3Reader pomReader = new MavenXpp3Reader();

    private final Map<Path, CacheEntry> modelCache = new HashMap<>();

    public Optional<Model> readPomModel(String path) {
        try (Stream<Path> pathStream = Files.find(Paths.get(path), 1, (p, a) -> p.endsWith("pom.xml"))) {
            Optional<Path> pomPath = pathStream.findFirst();
            return pomPath.flatMap(this::pathToModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProperty(Path pomPath, String propertyName, String value) {
        try (Stream<String> pomLines = Files.lines(pomPath)) {
            // TODO: Replace with better solution using Document and XPath
            String pomContent = pomLines.collect(Collectors.joining("\n"));

            pomContent = pomContent.replaceFirst(
                    String.format("<%s>.*</%s>", propertyName, propertyName),
                    String.format("<%s>%s</%s>", propertyName, value, propertyName)
            );

            Files.write(pomPath, pomContent.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Optional<Model> pathToModel(Path pomPath) {
        boolean updateModel = false;
        Model model = null;
        try {
            log.info("Parsing pom: {}", pomPath);
            LocalDateTime lastModifiedTime = getLastModifiedTime(pomPath);

            if (this.modelCache.containsKey(pomPath)) {
                CacheEntry cacheEntry = this.modelCache.get(pomPath);
                if (cacheEntry.getLastModifiedTime().isBefore(lastModifiedTime)) {
                    updateModel = true;
                } else {
                    model = cacheEntry.getModel();
                }
            } else {
                updateModel = true;
            }

            if (updateModel) {
                File pomFile = pomPath.toFile();
                model = pomReader.read(new FileReader(pomFile));
                model.setPomFile(pomFile);

                this.modelCache.put(pomPath, new CacheEntry().setModel(model).setLastModifiedTime(lastModifiedTime));
            }
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

        return StreamSupport.stream(Arrays.stream(files.orElse(new File[]{})).spliterator(), true)
                .map(File::getAbsolutePath)
                .map(this::readPomModel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private LocalDateTime getLastModifiedTime(Path path) {
        try {
            BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime fileTime = basicFileAttributes.lastModifiedTime();
            return LocalDateTime.ofInstant(fileTime.toInstant(), ZoneOffset.UTC);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @Accessors(chain = true)
    private static class CacheEntry {

        private Model model;

        private LocalDateTime lastModifiedTime;

    }

}
