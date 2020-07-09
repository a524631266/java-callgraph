package gr.zhangll.javadynamic.part01;

public class Hello {
    public void say(){
        System.out.println("hello");
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String typeName = Demo.class.getTypeName();
        System.out.println(typeName);

    }
}
