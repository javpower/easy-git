package com.gc.easy.git.core.impl;

import com.gc.easy.git.core.GitCommand;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
public class BizCommand implements GitCommand {
    private final Object param;
    private final Consumer<Object> consumer;
    public BizCommand(Object param,Consumer<Object> consumer) {
        this.param = param;
        this.consumer=consumer;
    }
    @Override
    public void execute() throws GitAPIException, IOException {
        consumer.accept(param);
    }
}
