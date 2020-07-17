package gr.zhangll.javadynamic.part01.javassist;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 主要功能是重置类加载器
 */
public class VerboseClassLoader extends URLClassLoader {
    public VerboseClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        System.out.println("loadClass::: " + name);
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> aClass = super.findClass(name);
        System.out.println("find class: loaded " + name + " from this loader");
        return aClass;
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (args.length >= 1) {
            try {
                // get paths to be used for loading
                ClassLoader base =
                        ClassLoader.getSystemClassLoader();
                URL[] urls;
                if (base instanceof URLClassLoader) {
                    urls = ((URLClassLoader)base).getURLs();
                } else {
                    urls = new URL[]
                            { new File(".").toURI().toURL() };
                }

                // list the paths actually being used
                System.out.println("Loading from paths:");
                for (int i = 0; i < urls.length; i++) {
                    System.out.println(" " + urls[i]);
                }

                // load target class using custom class loader
                VerboseClassLoader loader =
                        new VerboseClassLoader(urls, base.getParent());
                Class clas = loader.loadClass(args[0]);

                // invoke "main" method of target class
                Class[] ptypes =
                        new Class[] { args.getClass() };
                Method main =
                        clas.getDeclaredMethod("main", ptypes);
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                Thread.currentThread().
                        setContextClassLoader(loader);
                main.invoke(null, new Object[] { pargs });

            } catch ( MalformedURLException e ) {
                e.printStackTrace();
            } catch ( NoSuchMethodException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println
                    ("Usage: VerboseLoader main-class args...");
        }
    }

}
