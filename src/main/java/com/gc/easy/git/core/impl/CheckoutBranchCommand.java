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
public class CheckoutBranchCommand implements GitCommand {
    private GitContext context;

    public CheckoutBranchCommand(GitContext context) {
        this.context = context;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        try (Git git = new Git(new FileRepository(new File(context.getLocalPath() + "/.git")))) {
            git.checkout()
                    .setName(context.getBranchName())
                    .call();
            log.info("Successfully checked out to branch: {}", context.getBranchName());
        }
    }
}
