package com.gc.easy.git.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GitInfo implements Serializable
{
    private static final long serialVersionUID = -4864847400188602713L;
    

    private String username;
    

    private String password;


    private String email;
    

    private String remoteGit;


    private String localPath;
}
