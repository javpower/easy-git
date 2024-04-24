package com.gc.easy.git.core.plan;


import com.gc.easy.git.core.GitCommand;
import com.gc.easy.git.core.impl.*;
import com.gc.easy.git.model.GitCommandType;
import com.gc.easy.git.model.GitContext;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommandPlan {
    private final List<PlannedCommand> plannedCommands = new LinkedList<>();
    private GitContext context;

    private Object res;
    public CommandPlan context(GitContext context) {
        this.context=context;
        return this;
    }
    public CommandPlan then(GitCommand command) {
        plannedCommands.add(new PlannedCommand(command, () -> true));
        return this;
    }
    public CommandPlan then(GitCommandType command) {
        plannedCommands.add(new PlannedCommand(createCommand(command), () -> true));
        return this;
    }
    public CommandPlan consumer(Object t,Consumer<Object> consumer) {
        consumer.accept(t);
        return this;
    }
    public CommandPlan function(Object t, Function<Object,Object> function) {
       this.res= function.apply(t);
        return this;
    }
    public CommandPlan supplier(Supplier<Object> supplier) {
        this.res= supplier.get();
        return this;
    }
    public Object getRes(){
        return this.res;
    }

    private GitCommand createCommand(GitCommandType commandType) {
        switch (commandType) {
            case CLONE:
                return new CloneCommand(context);
            case PULL:
                return new PullCommand(context);
            case PUSH:
                return new PushCommand(context);
            case CREATE_BRANCH:
                return new CreateBranchCommand(context);
            case CHECKOUT_BRANCH:
                return new CheckoutBranchCommand(context);
            case COMMIT:
                return new CommitCommand(context);
            case ADD:
                return new AddCommand(context);
            case MERGE:
                return new MergeCommand(context);
            default:
                throw new IllegalArgumentException("Unsupported command type");
        }
    }

    public CommandPlan thenIf(GitCommand command, Supplier<Boolean> condition) {
        plannedCommands.add(new PlannedCommand(command, condition));
        return this;
    }
    public CommandPlan thenIf(GitCommandType command, Supplier<Boolean> condition) {
        plannedCommands.add(new PlannedCommand(createCommand(command), condition));
        return this;
    }
    public void execute() {
        for (PlannedCommand plannedCommand : plannedCommands) {
            if (plannedCommand.condition.get()) {
                try {
                    System.out.printf(plannedCommand.command.toString());
                    plannedCommand.command.execute();
                    System.out.println("Command executed successfully.");
                } catch (GitAPIException | IOException e) {
                    System.out.println("Error executing command: " + e.getMessage());
                }
            }
        }
    }

    private static class PlannedCommand {
        final GitCommand command;
        final Supplier<Boolean> condition;

        PlannedCommand(GitCommand command, Supplier<Boolean> condition) {
            this.command = command;
            this.condition = condition;
        }
    }
}
