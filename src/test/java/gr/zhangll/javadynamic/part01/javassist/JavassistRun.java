package gr.zhangll.javadynamic.part01.javassist;

import javassist.*;

import static gr.zhangll.javadynamic.part01.javassist.JassistTiming.addTiming;

public class JavassistRun {
    public static void main(String[] args) throws Throwable {
        if (args.length >= 1) {
            // observer
//            Translator observer = new VerboseTranslator();
            Translator observer2 = new SimpleTranslator(args[0], args[1]);
            // 注册 pool 和 observer
            ClassPool pool = ClassPool.getDefault();
            Loader loader = new Loader(pool);
//            loader.addTranslator(pool , observer);
            loader.addTranslator(pool, observer2);

            // 默认执行main方法，代码很简约
            String[] pargs = new String[args.length - 2];
            System.arraycopy(args, 2, pargs, 0 ,pargs.length);
//            loader.run(args[0], pargs);
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

    public static class SimpleTranslator implements Translator {

        private final String m_className;
        private  final String m_methodName;

        public SimpleTranslator(String m_className, String m_methodName) {
            this.m_className = m_className;
            this.m_methodName = m_methodName;
        }


        @Override
        public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

        }

        @Override
        public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
            System.out.println("onWrite called for " + classname);
            if(classname == m_className){
                addTiming(pool.get(m_className), m_methodName);
            }
        }
    }
}
