package gr.zhangll.javadynamic.part01;

public class ThreadClassDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Class<?> aClass = contextClassLoader.loadClass("gr.zhangll.javadynamic.part01.Hello");
        Hello o = (Hello)aClass.newInstance();
    }
}
