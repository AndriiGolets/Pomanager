package site.golets.pomanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Service;
import site.golets.pomanager.service.GitManagementService;
import site.golets.pomanager.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FileSystemGitManagementServiceImpl implements GitManagementService {

    private static final String GIT_DIR_NAME = ".git";

    @Override
    public String getBranchName(String gitRepositoryPath) {
        PathUtils.existsFailFast(gitRepositoryPath);

        File repositoryDir = Paths.get(gitRepositoryPath).toFile();

        if (!isGitRepository(repositoryDir)) {
            throw new IllegalStateException("Provided path is not a Git repository.");
        }

        try (Git git = Git.open(repositoryDir)) {
            log.info("Get current branch for repository [{}]", gitRepositoryPath);
            return git.getRepository().getBranch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkoutAndPull(String gitRepositoryPath, String branch, boolean hard) {
        String currentBranch = getBranchName(gitRepositoryPath);

        File repositoryDir = Paths.get(gitRepositoryPath).toFile();

        log.info("Performing checkout & pull on [{}] with hard reset for repository [{}]", branch, gitRepositoryPath);

        try (Git git = Git.open(repositoryDir)) {
            Set<String> cleanResult = git.clean()
                    .setForce(true)
                    .setCleanDirectories(true)
                    .call();
            log.info("clean -fd, cleaned [{}]", cleanResult);

            git.reset()
                    .setMode(ResetCommand.ResetType.HARD)
                    .call();
            log.info("reset --hard");

            if (!currentBranch.equals(branch)) {
                Ref checkoutResult = git.checkout()
                        .setName(branch)
                        .call();
                log.info("checkout {}({})", branch, checkoutResult.getName());
            }

            PullResult pullResult = git.pull()
                    .setRemote("origin")
                    .setRemoteBranchName(branch)
                    .call();
            log.info("pull origin {}: {}", branch, pullResult);
        } catch (IOException | GitAPIException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkoutAndPull(List<String> gitRepositoryPaths, String branch, boolean hard) {
        for (String gitRepositoryPath : gitRepositoryPaths) {
            checkoutAndPull(gitRepositoryPath, branch, hard);
        }
        return true;
    }

    private boolean isGitRepository(File repositoryDir) {
        if (!repositoryDir.isDirectory()) {
            return false;
        }

        String[] directoryList = repositoryDir.list();

        if (directoryList == null) {
            throw new IllegalStateException("Provided directory is empty.");
        }

        return Arrays.stream(directoryList)
                .anyMatch(n -> n.endsWith(GIT_DIR_NAME));
    }

}
