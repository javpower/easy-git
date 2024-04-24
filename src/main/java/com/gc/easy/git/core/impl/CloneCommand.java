package com.gc.easy.git.core.impl;

import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.model.GitContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

@Slf4j
public class CloneCommand implements GitCommand {
    private GitContext context;

    public CloneCommand(GitContext context) {
        this.context = context;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        if (!new File(context.getLocalPath()).exists())
        { Git.cloneRepository()
                .setURI(context.getGitInfo().getRemoteGit())
                .setBranch(context.getBranchName())  // 使用动态分支名称
                .setCredentialsProvider(context.getCredentialsProvider())
                .setDirectory(new File(context.getLocalPath()))
                .call();
            log.info("Repository cloned successfully to branch: {}", context.getBranchName());
        }
    }
}