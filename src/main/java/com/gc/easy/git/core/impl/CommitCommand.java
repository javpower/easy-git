package com.gc.easy.git.core.impl;


import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.model.GitContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.IOException;

@Slf4j
public class CommitCommand implements GitCommand {
    private GitContext context;

    public CommitCommand(GitContext context) {
        this.context = context;
    }
    @Override
    public void execute() throws GitAPIException, IOException {
        try (Git git = new Git(new FileRepository(context.getLocalPath() + "/.git"))) {
            String remark = context.getCommitMessage();
            git.commit()
                    .setAmend(true).setAll(true)
                    .setAuthor(context.getGitInfo().getUsername(), context.getGitInfo().getEmail())
                    .setMessage(remark)
                    .call();
            log.info("Committed locally: {}", remark);
        }
    }
}



