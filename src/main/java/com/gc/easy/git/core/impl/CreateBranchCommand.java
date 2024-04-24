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
public class CreateBranchCommand implements GitCommand {
    private GitContext context;

    public CreateBranchCommand(GitContext context) {
        this.context = context;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        try (Git git = new Git(new FileRepository(new File(context.getLocalPath() + "/.git")))) {
            // 创建分支，默认不切换
            git.branchCreate()
                    .setName(context.getBranchName())
                    .call();
            log.info("Successfully created branch: {}", context.getBranchName());
        }
    }
}
