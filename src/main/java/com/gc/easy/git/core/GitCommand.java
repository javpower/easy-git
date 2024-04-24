package com.gc.easy.git.core;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface GitCommand {
    void execute() throws GitAPIException, IOException;
}

