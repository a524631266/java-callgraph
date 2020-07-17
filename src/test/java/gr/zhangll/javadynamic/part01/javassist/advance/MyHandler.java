package gr.zhangll.javadynamic.part01.javassist.advance;

public class MyHandler {
    /**
     *
     * @param bean 必须要匹配要监控的类，所以比较难搞，只能动态创建方法
     * @param value
     */
    public static void reportSet(Bean bean, String value){
        System.out.println("Call to set value " + value);
    }
}
