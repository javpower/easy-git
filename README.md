<h1 align="center">EasyGit - git客户端框架</h1>

直接添加以下maven依赖即可

```xml
<dependency>
    <groupId>io.github.javpower</groupId>
    <artifactId>easy-git-spring-boot-starter</artifactId>
    <version>2.8.0.0</version>
</dependency>
```
### Reference Documentation

    
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



For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.13/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.13/maven-plugin/reference/html/#build-image)

