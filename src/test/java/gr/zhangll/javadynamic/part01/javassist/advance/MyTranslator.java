package gr.zhangll.javadynamic.part01.javassist.advance;

import javassist.*;

public class MyTranslator implements Translator {
    private final String m_className;
    private final String m_methodName;
    private CodeConverter converter;

    public MyTranslator(String m_className, String m_methodName) {
        this.m_className = m_className;
        this.m_methodName = m_methodName;
    }

    //    private final String m_classMethodName;
    @Override
    public void start(ClassPool pool) throws NotFoundException, CannotCompileException {

    }

    @Override
    public void onLoad(ClassPool pool, String classname) throws NotFoundException, CannotCompileException {
        System.out.println("loading classname ::" + classname);
//        if(classname == m_className){
        CtClass ctClass = pool.get(classname);
        // 为每一个class装配一个converter
        ctClass.instrument(converter);
//            CtMethod method = ctClass.getMethod(m_methodName);

//        }
    }

    public void setConverter(CodeConverter converter) {
        this.converter = converter;
    }
}
