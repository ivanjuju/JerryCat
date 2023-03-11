package com.jerryCat.config;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author ivanzhu
 */
@Getter
@AllArgsConstructor
public class ApplicationConfiguration {

    /**
     * 启动端口
     */
    private int port;

    /**
     * servlet路径
     */
    private String baseServletPackage;

    @SneakyThrows
    public static ApplicationConfiguration initial(Class<?> applicationClass) {
        //初始化配置
        // 读取配置文件Server.xml中的配置信息
        InputStream in = applicationClass.getClassLoader().getResourceAsStream("server.xml");
        if (in == null) {
            throw new RuntimeException("请在resource维护server.xml配置文件");
        }
        //获取配置文件输入流
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(in);
        //使用SAXReader + XPath读取端口配置

        // 获取端口号
        Element configEle = (Element) doc.selectSingleNode("//server");
        Node portNode = configEle.selectSingleNode("//port");
        Integer port = Integer.valueOf(portNode.getText());

        // 获取servlet包路径
        Node servletPackageNode = configEle.selectSingleNode("//servletPackage");
        String servletPackagePath = String.valueOf(servletPackageNode.getText());
        return new ApplicationConfiguration(port, servletPackagePath);
    }
}
