package com.gc.easy.git.core.plan;


import com.gc.easy.git.core.impl.BizCommand;
import com.gc.easy.git.model.GitCommandType;
import com.gc.easy.git.model.GitContext;
import com.gc.easy.git.model.GitInfo;

public class GitCommandInvoker {
    public static void main(String[] args) {
        //git项目信息
        GitInfo info=new GitInfo();
        info.setEmail("");
        info.setRemoteGit("");
        info.setUsername("");
        info.setPassword("");
        info.setLocalPath("");
        // 配置具体的 Git 上下文
        GitContext context = GitContext.builder().gitInfo(info).
                commitMessage("ab commit").build().end();
        CommandPlan plan = new CommandPlan();
        plan.context(context).
        then(GitCommandType.CLONE).
        then(GitCommandType.PULL).
        then(new BizCommand(info.getLocalPath(),(a)->{

        })).
        then(GitCommandType.ADD).
        then(GitCommandType.COMMIT).
        then(GitCommandType.PUSH).execute();
    }

}

