# 报名系统

## 主技术栈

- [Spring Boot 1.5+](http://projects.spring.io/spring-boot/)
- [MyBatis 3.4+](http://www.mybatis.org/mybatis-3/)
- [Shiro 1.3+](http://shiro.apache.org/)

## 开发工具

- [Gradle 4.6+](http://gradle.org/)
- [JetBrains Intelli IDEA 2018.1+](https://www.jetbrains.com/idea/) (Plugins: EditorConfig, Lombok)
- [EditorConfig](http://editorconfig.org/)
- [Lombok](https://projectlombok.org/features/index.html)

## 测试工具

- [JUnit4](https://junit.org/junit4/)
- [Spock](http://spockframework.org/)
- [JMeter](http://jmeter.apache.org/)

## 开发环境

1. hosts
```
192.168.0.152 elpdtman
192.168.0.152 elpdtmvn
```
1. Install Gradle and IDEA
1. Run `git clone https://github.com/doGe-personal/sign_up_svr.git`
1. Run `gradle idea`
1. Open `sign-up-svr.ipr` file in IDEA
    1. Plugin: Lombok
    1. Preferences: Compiler>Annotation Processors: Enable annotation processing
    1. Preferences: Build Tools>Gradle: Use local gradle distribution: <PATH/TO/YOUR/GRADLE>

### 开发

1. Run `gradle bootRun`
1. Open `http://127.0.0.1:9000/signup/`

关于 Spring Boot DevTools 的 Build/Restart 的不足之处及应对方法

> 进入IoC容器内对象发生修改时依赖于该对象的依赖注入会失败, 对应最简单办法是修改`resoureces`下的任一配置文件即可触发reload，
>
> 此时该注入就OK了（其实修改任意非容器对象或接口再编译也能触发重新加载）。

### 检查

> [FindBugs](https://docs.gradle.org/current/userguide/findbugs_plugin.html)

1. Run `gradle check`
1. Output `build/reports/findbugs/`

### 测试

1. Run `gradle test`
1. Output `build/reports/tests/`

### 打包

1. Run `/build-war.sh`
1. Output `build/libs/*.war`

or

1. Run `/build-app.sh`
1. Output `build/libs/*.jar`

## 参考资料

- [开发入手指引](http://redmine.elitesland.com:5313/redmine/issues/15844)
- [MyBatis3 FAQ](https://github.com/mybatis/mybatis-3/wiki/FAQ)
