package com.gc.easy.git.core.impl;


import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.model.GitContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MergeCommand implements GitCommand {
    private GitContext context;

    public MergeCommand(GitContext context) {
        this.context = context;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        try (Git git = new Git(new FileRepository(new File(context.getLocalPath() + "/.git")))) {
            MergeResult mergeResult = git.merge()
                .include(git.getRepository().resolve(context.getBranchName()))
                .call();
            if (mergeResult.getMergeStatus().isSuccessful()) {
                log.info("Branch {} merged successfully into current branch.", context.getBranchName());
            } else {
                log.error("Merge failed with status: {}", mergeResult.getMergeStatus());
            }
        } catch (Exception e) {
            log.error("Failed to merge branch: {}", context.getBranchName(), e);
            throw e;
        }
    }
}
