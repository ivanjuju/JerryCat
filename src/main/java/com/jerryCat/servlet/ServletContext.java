package com.jerryCat.servlet;

import com.jerryCat.net.JerryServlet;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;

/**
 * @author ivanzhu
 */
public class ServletContext {
    private static Map<String, String> servletCacheMap = new HashMap<>();
    private static Map<String, JerryServlet> servletBeanMap = new HashMap<>();

    public static void cacheServlet(String packagePath, Class<?> applicationClass) {
        URL resource = applicationClass.getClassLoader()
            // com.abc.webapp => com/abc/webapp ;
            .getResource(packagePath.replaceAll("\\.", "/"));
        if (resource == null) {
            return;
        }
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null) {
                return;
            }

            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    cacheServlet(packagePath + "." + childFile.getName(), applicationClass);
                } else {
                    putCache(childFile, packagePath);
                }
            }
        } else if (file.getName().endsWith(".class")){
            putCache(file, packagePath);
        }
    }

    private static void putCache(File childFile, String packagePath) {
        String servletName = childFile.getName().replaceAll(".class", "");
        if (servletCacheMap.containsKey(servletName.toLowerCase())) {
            throw new RuntimeException(String.format("servlet名称重复，请考虑修改重复的servlet名称【%s】", servletName));
        }
        servletCacheMap.put(
            servletName.toLowerCase(),
            packagePath + "." + servletName);
    }

    public static JerryServlet doGetServlet(String servletName) {
        String servletLowerName = servletName.toLowerCase();
        if (!servletCacheMap.containsKey(servletLowerName)) {
            return null;
        }
        if (servletBeanMap.containsKey(servletLowerName)) {
            return servletBeanMap.get(servletLowerName);
        }
        return doCreateServletBean(servletLowerName);
    }

    @SneakyThrows
    private static synchronized JerryServlet doCreateServletBean(String servletLowerName) {
        if (servletBeanMap.containsKey(servletLowerName)) {
            return servletBeanMap.get(servletLowerName);
        }
        String servletPath = servletCacheMap.get(servletLowerName);
        Class<?> servlerClass = Class.forName(servletPath);
        JerryServlet jerryServlet = (JerryServlet) servlerClass.newInstance();
        servletBeanMap.put(servletLowerName, jerryServlet);
        return jerryServlet;
    }
}
