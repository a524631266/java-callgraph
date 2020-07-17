package gr.zhangll.javadynamic.part01.javassist;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * argv = {gr.zhangll.javadynamic.part01.javassist.StringBuilderDemo, buildString}
 * https://developer.ibm.com/zh/articles/j-dyn0916/
 */
public class JassistTiming {
    public static void main(String[] argv) {
        if(argv.length == 2 ){
            try {
                // 1.获取类
                CtClass ctClass = ClassPool.getDefault().get(argv[0]);

                // 2. 添加冬天类习惯
                addTiming(ctClass, argv[1]);
                ctClass.writeFile();
//                Stream.of(ctClass.getDeclaredMethods())
//                        .peek(method -> System.out.println("method::::"+method.getName()))
//                        .collect(Collectors.toList());
                Class aClass = ctClass.toClass();
                Method main = aClass.getMethod("main",String[].class);

                main.invoke(aClass.newInstance(),
                        (Object) new String[]{
                            "1000", "2000", "3000", "4000", "6000", "7000"
                        });
                System.out.println("Added timing to method " +
                        argv[0] + "." + argv[1]);
            } catch ( NotFoundException e ) {
                e.printStackTrace();
            } catch ( CannotCompileException e ) {
                e.printStackTrace();
            } catch ( IOException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            } catch ( NoSuchMethodException e ) {
                e.printStackTrace();
            } catch ( InvocationTargetException e ) {
                e.printStackTrace();
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usage: JassistTiming class method-name");
        }
    }
//    /**
//     * 2.使用拦截器，即一个同样的名称来表示原函数，并
//     * 替换为新的寒素
//     * @param length
//     * @return
//     */
//    private String buildString$impl(int length) {
//        String result = "";
//        for (int i = 0; i < length; i++) {
//            result += (char)(i%26 + 'a');
//        }
//        return result;
//    }
//    private String buildString2(int length) {
//        long start = System.currentTimeMillis();
//        String result = buildString$impl(length);
//        System.out.println("Call to buildString took " + (System.currentTimeMillis()-start) + " ms.");
//        return result;
//    }
    private static void addTiming(CtClass aClass, String oldMethodName) throws NotFoundException, CannotCompileException {
        CtMethod oldMethod = aClass.getDeclaredMethod(oldMethodName);

        // 1. 改名字
        String newMethodName = oldMethodName + "$impl";
        oldMethod.setName(newMethodName);

        // 2. 创建一个method

        CtMethod newMethod = CtNewMethod.copy(oldMethod, oldMethodName, aClass, null);

        // 构建替代的模块
        StringBuilder body = new StringBuilder();
        body.append("{\n");
        body.append("long start = System.currentTimeMillis();");
        body.append("\n");
        String returnType = oldMethod.getReturnType().getName();
        if(!returnType.equals("void")){
            // $$代表输入参数
//            body.append(returnType+ " result = ");
            body.append("int result = ");
        }
        body.append(newMethodName + "($$);\n");
        body.append("System.out.println(\"Call to method " + oldMethodName +
                " took \" +\n (System.currentTimeMillis()-start) + " +
                "\" ms.\");\n");

        if (!"void".equals(returnType)) {
            body.append("return result;\n");
        }
        body.append("}");


        // 3. 添加body到newMethod
        newMethod.setBody(body.toString());
        aClass.addMethod(newMethod);

        System.out.println("Interceptor method body:");
        System.out.println(body.toString());
    }
}
