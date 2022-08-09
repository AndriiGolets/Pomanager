package site.golets.pomanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevWalkUtils;
import org.eclipse.jgit.revwalk.filter.RevFilter;
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
            return git.getRepository().getBranch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getBranchStatus(String gitRepositoryPath, String relativeBranch) {
        String branchName = getBranchName(gitRepositoryPath);

        File repositoryDir = Paths.get(gitRepositoryPath).toFile();

        int aheadCount, behindCount;

        try (Git git = Git.open(repositoryDir)) {
            try (RevWalk walk = new RevWalk(git.getRepository())) {
                String fullLocalBranchName = Constants.R_HEADS + branchName;
                String fullRelativeBranchName = Constants.R_HEADS + "origin/" + relativeBranch;
                RevCommit localCommit = walk.parseCommit(git.getRepository().exactRef(fullLocalBranchName).getObjectId());
                Ref fullRelativeBranchRef = git.getRepository().exactRef(fullRelativeBranchName);
                if (fullRelativeBranchRef == null) {
                    fullRelativeBranchName = Constants.R_HEADS + relativeBranch;
                    fullRelativeBranchRef = git.getRepository().exactRef(fullRelativeBranchName);
                }
                RevCommit trackingCommit = walk.parseCommit(fullRelativeBranchRef.getObjectId());
                log.info("Tracking commit different between {} and {}", fullLocalBranchName, fullRelativeBranchRef);
                walk.setRevFilter(RevFilter.MERGE_BASE);
                walk.markStart(localCommit);
                walk.markStart(trackingCommit);
                RevCommit mergeBase = walk.next();
                walk.reset();
                walk.setRevFilter(RevFilter.ALL);
                aheadCount = RevWalkUtils.count(walk, localCommit, mergeBase);
                behindCount = RevWalkUtils.count(walk, trackingCommit, mergeBase);
            }
            return aheadCount > 0 ? aheadCount : behindCount > 0 ? -behindCount : 0;
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
