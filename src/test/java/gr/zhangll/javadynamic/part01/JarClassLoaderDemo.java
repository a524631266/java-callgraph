package gr.zhangll.javadynamic.part01;

import com.zhangll.core.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoaderDemo {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL url = new File("E:\\github\\javacore\\everypath\\target\\everypath-1.0-SNAPSHOT.jar").toURL();
        System.out.println("url:" + url);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        Class<?> aClass = urlClassLoader.loadClass("com.zhangll.core.App");
        AppSay o = (AppSay) aClass.newInstance();
        o.say();
    }
}
