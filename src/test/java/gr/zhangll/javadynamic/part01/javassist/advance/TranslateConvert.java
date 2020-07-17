package gr.zhangll.javadynamic.part01.javassist.advance;

import javassist.*;

public class TranslateConvert {
    /**
     * args[0] = classname 主要要检测的类 Bean
     * args[1] = methodname 主要用来检测那个方法需要被检测 setA
     * args[2] =  mainclassname main主入口方法
     * other 为main的参数 BeanTest
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        if(args.length >= 3){
            String origClassName = args[0];
            String origMethodName = args[1];
            String origMainName = args[2];


            // 创建pool和loader
            ClassPool pool = ClassPool.getDefault();
            Loader loader = new Loader(pool);

            // 创建 CodeConverter
            CodeConverter converter = new CodeConverter();
            // 查找orgimethed
            CtMethod origMethod = pool.get(origClassName)
                                    .getDeclaredMethod(origMethodName);

            // 配置aop插入代码
            CtMethod insertMethod = pool.get(MyHandler.class.getName())
                                    .getDeclaredMethod("reportSet");
            converter.insertBeforeMethod(origMethod, insertMethod);

            // 装配code转换器
            MyTranslator translator = new MyTranslator(origMainName, origMethodName);
            translator.setConverter(converter);

            loader.addTranslator(pool, translator);

            // 执行对应classname的main方法
            String[] pargs = new String[args.length - 3];
            System.arraycopy(args,3, args,0, pargs.length);
            loader.run(origMainName, pargs);

        }else {
            System.out.println("Usage: TranslateConvert " +
                    "clas-name set-name main-class args...");
        }
    }


}
