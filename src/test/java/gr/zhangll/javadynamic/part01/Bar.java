package gr.zhangll.javadynamic.part01;

public class Bar {
    public Bar( String a, String b ) {
        System.out.println( "bar! "+a+" "+b );
        new Baz( a, b );

        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            System.out.println(contextClassLoader);
            Class<?> aClass = contextClassLoader.loadClass("gr.zhangll.javadynamic.part01.Boo");
            aClass.newInstance();
            Class booClass = Class.forName( "gr.zhangll.javadynamic.part01.Boo" );
            Object boo = booClass.newInstance();
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }
}
