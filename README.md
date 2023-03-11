# JerryCat
> 简易版Tomcat

## 快速入手
1. 引入JerryCat依赖
```xml
<dependency>
    <groupId>com.ivan</groupId>
    <artifactId>jerryCat</artifactId>
    <version>{version}</version>
</dependency>
```
2. 在resources中维护配置文件server.xml
> 文件内容如下
>```xml
><server>
>    <port>{服务启动端口号}</port>
>    <servletPackage>{servlet包路径}</servletPackage>
></server>
>```
3. 创建启动类，实例代码如下
```java
public class Demo {
    public static void main(String[] args) {
        ApplicationStarter.run(Demo.class);
    }
}
```
