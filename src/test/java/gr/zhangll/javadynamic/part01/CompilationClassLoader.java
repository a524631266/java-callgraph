package gr.zhangll.javadynamic.part01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class CompilationClassLoader extends ClassLoader {
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        return super.loadClass(name, resolve);
        System.out.println("开始加载:"+ name);
        // 1.查找是否有类
        Class<?> loadedClass = findLoadedClass(name);
        if(loadedClass == null) {
            // 查找本地磁盘是否有class
            loadedClass = findLoalDirctory(name);
        }
        if(loadedClass == null){
            loadedClass = findSystemClass(name);
        }
        if (loadedClass==null){
            new ClassNotFoundException("can not find the class:" + name);
        }
//        resolveClass(loadedClass);
        return loadedClass;
    }
    // When a class is requested, see if it exists on disk, in the current directory, or in the appropriate subdirectory.
    private Class<?> findLoalDirctory(String name) {
        Class<?> result = null;
        // java.lang.Object => java/lang/Object

        // 在jar包中会出现什么情况，这个this.getResource是什么
        String resourcepath = this.getResource(".").getPath();
        System.out.println(""+ resourcepath);
        String objpath = resourcepath.replace("file:/", "").replace("\\", "/") + name.replace(".", "/");

        String soruce_path = new File(this.getResource(".").getPath()).getParent();
        //        URL resource = ;
//        System.out.println(resource);
        String javafile = soruce_path + ".java";
        String classfile = objpath + ".class";

        File javaFile = new File(javafile);
        File classFile = new File(classfile);
        if(javaFile.exists()){
            // 当前java修改日期
            if(!classFile.exists() || (javaFile.lastModified()> classFile.lastModified())) {
                if (!compile(javaFile) && !classFile.exists()) {
                    new ClassNotFoundException("can not find the class:" + name);
                }
            }
        }
        System.out.println(javafile + ":::"+ javaFile.exists());
        try {
            byte[] bytes = getBytes(classfile);
            result = defineClass(name, bytes,0, bytes.length);
        } catch ( IOException e ) {
//            e.printStackTrace();
        }
        return result;
    }
    // Given a filename, read the entirety of that file from disk
    // and return it as a byte array.
    private byte[] getBytes( String filename ) throws IOException {
        // Find out the length of the file
        File file = new File( filename );
        long len = file.length();

        // Create an array that's just the right size for the file's
        // contents
        byte raw[] = new byte[(int)len];

        // Open the file
        FileInputStream fin = new FileInputStream( file );

        // Read all of it into the array; if we don't get all,
        // then it's an error.
        int r = fin.read( raw );
        if (r != len)
            throw new IOException( "Can't read all, "+r+" != "+len );

        // Don't forget to close the file!
        fin.close();

        // And finally return the file contents as an array
        return raw;
    }


    /**
     * 自动编译代码
     * @param javaFile
     * @return
     */
    private boolean compile(File javaFile) {
        Process process = null;
        System.out.println("compile : java"+javaFile);
        try {
            process = Runtime.getRuntime().exec("javac " + javaFile);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        try {
            process.waitFor();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
        int i = process.exitValue();
        return i == 0;
    }

    /**
     * 测试
     *
     * @param args
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        System.out.println(System.getProperty());
        CompilationClassLoader loader = new CompilationClassLoader();
        Thread.currentThread().setContextClassLoader(loader);
        Class<?> aClass = loader.loadClass("gr.zhangll.javadynamic.part01.Foo", true);

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);

        Class mainArgType[] = {(new String[0]).getClass()};
        Method main = aClass.getMethod("main", mainArgType);
        String progArgs[] = new String[]{"arg1", "arg2"};
        // 参数必须传一个object，否则会自动扩展参数列表导致参数个数不匹配的错误
        Object args2[] = {progArgs};
        main.invoke(null, args2);
    }
}
