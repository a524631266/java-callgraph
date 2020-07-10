package gr.zhangll.javadynamic.part01.reflection;

import java.lang.reflect.*;
import java.security.AccessControlException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Person{
    int age = 0;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}

class Person2{
    int age = 0;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
class TwoString {
    private String m_s1, m_s2;
    public TwoString(String s1, String s2) {
        m_s1 = s1;
        m_s2 = s2;
    }
}
public class ReflectionDemo {
    /**
     * 给反射的对象一个
     *
     * @param name
     */
    private static void increaseProperty(String name, Object value, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // componentType是代表了类型的泛型，比如array<Type> map<ComponetType>
        Class<?> componentType = null;
        if(value instanceof Integer){
            componentType = int.class;
        }else if (value instanceof  Float){
            componentType = float.class;
        }else if(value instanceof  Character){
            componentType = char.class;
        }else if(value instanceof Double){
            componentType = double.class;
        }else {
            componentType = value.getClass();
        };
        System.out.println(componentType);
        Method method = object.getClass().getMethod("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1), componentType);
        Object invoke = method.invoke(object, value);
    }

    private static void arrayComponent(Object OO){

//        Object OO = (Object) ps;
        System.out.println(OO);
        System.out.println(OO.getClass().getComponentType());
        Object o = Array.newInstance(OO.getClass().getComponentType(), 1);
        System.arraycopy(OO,0,o,0,1);
        System.out.println(o);
    }

    private static void testSecuriry() throws NoSuchFieldException, IllegalAccessException {
        TwoString ts = new TwoString("a", "b");
        Field m_s1 = ts.getClass().getDeclaredField("m_s1");
        // setAccessible ,access表示可以读写或者介入（接入）点，原有的私有权限被扩展
        // 如果你使用-Djava.security.manager 作为vm参数，那么任然会得到java.lang.reflect.ReflectPermission" "suppressAccessChecks"
        // if you add the JVM parameter -Djava.security.manager on the command line to enable a security manager, it will again fail, unless you define permissions for the ReflectSecurity class.
        // 详情请看 java.policy
        m_s1.setAccessible(true);
        //java.lang.IllegalAccessException:
        Object o = m_s1.get(ts);
        System.out.println(o);
    }
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Person2 person = new Person2();
        increaseProperty("age",2,person);
        Person person2 = new Person();
        System.out.println(person.getAge());
        // 动态扩展数组
        Map map = new HashMap();
        List<String> ps = new ArrayList<String>();
        ps.add("123");
        ps.add("234");
        arrayComponent(new int[]{1,2,3,4});

        // security
        // 检测时否开启
        // 获取当前的一个快照
        testSecuriry();
    }
}
