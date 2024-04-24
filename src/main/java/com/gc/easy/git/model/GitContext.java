package com.gc.easy.git.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.util.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitContext {
    private GitInfo gitInfo;
    private CredentialsProvider credentialsProvider;
    private String localPath;
    private String commitMessage;
    private String branchName;
    private String remoteAlias;

    public GitContext end(){

        if(StringUtils.isEmpty(this.branchName)){
            this.branchName="master";
        }
        if(StringUtils.isEmpty(this.remoteAlias)){
            this.remoteAlias="origin";
        }
        if(StringUtils.isEmpty(this.commitMessage)){
            this.commitMessage="auto commit";
        }
        if(StringUtils.isEmpty(this.localPath)){
            this.localPath=gitInfo.getLocalPath();
        }
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(gitInfo.getUsername(), gitInfo.getPassword());
        return this;
    }

}
