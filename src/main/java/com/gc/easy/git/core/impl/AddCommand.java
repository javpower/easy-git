package com.gc.easy.git.core.impl;


import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.model.GitContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@Slf4j
public class AddCommand implements GitCommand {
    private GitContext context;

    public AddCommand(GitContext context) {
        this.context = context;
    }
    @Override
    public void execute() throws GitAPIException, IOException {
        File rootDir = new File(context.getLocalPath());
        try (Git git = Git.open(rootDir)) {
            List<String> files = Arrays.asList(rootDir.list((dir, name) -> !name.endsWith(".git")));
            List<DiffEntry> diffEntries = git.diff().setPathFilter(PathFilterGroup.createFromStrings(files)).setShowNameAndStatusOnly(true).call();
            if (diffEntries == null || diffEntries.isEmpty()) {
                return;
            }
            // 被修改过的文件
            List<String> updateFiles = new ArrayList<String>();
            DiffEntry.ChangeType changeType;
            diff(diffEntries, updateFiles);
            // 将文件提交到git仓库中，并返回本次提交的版本号
            // 1、将工作区的内容更新到暂存区
            org.eclipse.jgit.api.AddCommand addCmd = git.add();
            for (String file : updateFiles) {
                addCmd.addFilepattern(file);
            }
            addCmd.call();
        }
    }

    public static void diff(List<DiffEntry> diffEntries, List<String> updateFiles) {
        DiffEntry.ChangeType changeType;
        for (DiffEntry entry : diffEntries) {
            changeType = entry.getChangeType();
            switch (changeType) {
                case ADD:
                case COPY:
                case RENAME:
                case MODIFY:
                    updateFiles.add(entry.getNewPath());
                    break;
                case DELETE:
                    updateFiles.add(entry.getOldPath());
                    break;
            }
        }
    }
}