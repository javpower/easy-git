package com.gc.easy.git.core.impl;


import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.model.GitContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.File;
import java.io.IOException;


@Slf4j
public class PushCommand implements GitCommand {
    private GitContext context;

    public PushCommand(GitContext context) {
        this.context = context;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        try (Git git = new Git(new FileRepository(new File(context.getLocalPath() + "/.git")))) {
            git.push()
                    .setRemote(context.getRemoteAlias())  // 使用动态远程别名
                    .setCredentialsProvider(context.getCredentialsProvider())
                    .call();
            log.info("Changes pushed successfully to remote: {}", context.getRemoteAlias());
        }
    }
}
