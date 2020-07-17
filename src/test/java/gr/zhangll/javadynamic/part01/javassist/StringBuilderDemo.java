package gr.zhangll.javadynamic.part01.javassist;

public class StringBuilderDemo {

//    private String buildString(int length) {
//        long start = System.currentTimeMillis();
//        String result = "";
//        for (int i = 0; i < length; i++) {
//            result += (char)(i%26 + 'a');
//        }
//        System.out.println("Call to buildString took " +
//                (System.currentTimeMillis()-start) + " ms.");
//        return result;
//    }

    /**
     * 字节码插桩
     * @param length
     * @return
     */
    private String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char) (i % 26 + 'a');
        }
        return result;
    }

    /**
     * 字节码插桩
     * @param length
     * @return
     */
    private String sayHello(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char) (i % 26 + 'a');
        }
        return result;
    }

    public static void main(String[] argv) {
        StringBuilderDemo inst = new StringBuilderDemo();
        for (int i = 0; i < argv.length; i++) {
            String result = inst.buildString(Integer.parseInt(argv[i]));
            System.out.println("Constructed string of length " +
                    result.length());
        }
    }
}
