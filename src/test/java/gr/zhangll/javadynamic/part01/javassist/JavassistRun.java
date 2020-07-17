package gr.zhangll.javadynamic.part01.javassist;

import javassist.*;

public class JavassistRun {
    public static void main(String[] args) throws Throwable {
        if (args.length >= 1) {
            // observer
            Translator observer = new VerboseTranslator();

            // 注册 pool 和 observer
            ClassPool pool = ClassPool.getDefault();
            Loader loader = new Loader(pool);
            loader.addTranslator(pool , observer);

            // 默认执行main方法，代码很简约
            String[] pargs = new String[args.length - 1];
            System.arraycopy(args, 1, pargs, 0,pargs.length);
            loader.run(args[0], pargs);



        } else {
            System.out.println
                    ("Usage: JavassistRun main-class args...");
        }
    }

    public static class VerboseTranslator implements Translator {


        @Override
        public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

        }

        @Override
        public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
            System.out.println("onWrite called for " + classname);
        }
    }

}
