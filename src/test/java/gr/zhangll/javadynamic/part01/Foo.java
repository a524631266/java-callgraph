package gr.zhangll.javadynamic.part01;

public class Foo {
    static public void main( String args[] ) throws Exception {
        System.out.println( "foo! "+args[0]+" "+args[1] );
//        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//        Thread.currentThread().setContextClassLoader(new CompilationClassLoader());

//        Class<?> aClass = new CompilationClassLoader().loadClass("gr.zhangll.javadynamic.part01.Bar", true);
//        aClass.newInstance();
        new Bar( args[0], args[1] );
    }
}
