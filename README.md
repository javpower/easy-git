# 📚 EasyGit: 声明式Git操作工具包

`EasyGit` 是一个简化Git操作的声明式工具包，它允许开发者以一种直观和声明式的方式执行Git命令。无论是克隆仓库、拉取更新、推送代码，还是更复杂的Git工作流，`EasyGit` 都提供了一种简单而强大的解决方案。

## 🌟 特性亮点

- **声明式API**：通过直观的声明式API简化Git命令的执行。
- **链式操作**：支持链式调用，使得Git命令序列易于编写和理解。
- **条件执行**：基于条件的Git命令执行，提供了更灵活的控制。
- **异常处理**：自动处理`GitAPIException`和`IOException`，简化异常管理。
- **扩展性**：允许自定义Git命令，以适应特定的业务需求。

## 🛠️ 安装指南

### 添加Maven依赖

在项目的 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.javpower</groupId>
    <artifactId>easy-git-spring-boot-starter</artifactId>
    <version>2.8.0.0</version>
</dependency>
```

## 🔧 使用指南

### 初始化Git上下文

创建 `GitContext` 对象，配置Git操作所需的信息：

```java
GitInfo info = new GitInfo();
info.setEmail("your_email@example.com");
info.setRemoteGit("https://github.com/user/repo.git");
info.setUsername("username");
info.setPassword("password");
info.setLocalPath("/path/to/local/repo");

GitContext context = GitContext.builder()
        .gitInfo(info)
        .commitMessage("Your commit message")
        .build();
```

### 构建命令计划

使用 `CommandPlan` 构建并执行Git命令序列：

```java
CommandPlan plan = new CommandPlan();
plan.context(context)
    .then(GitCommandType.CLONE)
    .then(GitCommandType.PULL)
    .then(new BizCommand(info.getLocalPath(), (a) -> {
    // 自定义业务逻辑
    }))
    .then(GitCommandType.ADD)
    .then(GitCommandType.COMMIT)
    .then(GitCommandType.PUSH);
```
### 自定义业务逻辑
BizCommand 是一个自定义命令的示例，您可以根据需要实现自己的业务逻辑：
```java
public class BizCommand extends GitCommand {
private final String localPath;
private final Consumer<String> bizLogic;

    public BizCommand(String localPath, Consumer<String> bizLogic) {
        this.localPath = localPath;
        this.bizLogic = bizLogic;
    }

    @Override
    public void execute() throws GitAPIException, IOException {
        // 执行自定义业务逻辑
        bizLogic.accept(localPath);
    }
}
```
### 执行命令计划

调用 `execute` 方法执行构建的Git命令序列：

```java
plan.execute();
```

### 条件命令执行

使用 `thenIf` 方法根据条件执行Git命令：

```java
plan.thenIf(GitCommandType.MERGE, () -> someConditionCheck());
```

### 自定义业务逻辑

通过 `consumer`、`function` 和 `supplier` 方法在命令序列中插入自定义逻辑：

```java
plan.consumer(someObject, obj -> {
    // 处理业务逻辑
});

plan.function(someObject, obj -> {
    // 执行转换逻辑
    return transformedObject;
});

plan.supplier(() -> {
    // 提供业务逻辑结果
    return resultObject;
});
```

## 📜 注意事项

- 本项目尚未声明开源许可证，使用时请遵守相关法律法规。
- 确保您的Git客户端已正确配置，并且可以无密码访问远程仓库。
- 在生产环境中使用前，请进行充分的测试以确保系统稳定性。

## 🤝 贡献指南

我们非常欢迎社区贡献，以下是参与 `easy-git` 的步骤：

1. **克隆仓库**：Fork并克隆 `easy-git` 仓库。
2. **开发新特性**：创建一个新分支并实现您的特性或修复bug。
3. **提交更改**：提交您的更改，并确保包含测试以验证您的代码。
4. **创建Pull Request**：向 `easy-git` 的主仓库发起Pull Request。

## 📜 许可证

项目许可证尚未明确，使用前请查看项目页面的 `LICENSE` 文件。

## 🙏 致谢

感谢 `JGit` 团队提供的出色Git库，以及所有为 `easy-git` 做出贡献的开发者。