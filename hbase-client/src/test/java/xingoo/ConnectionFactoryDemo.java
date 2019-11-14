package xingoo;

import java.lang.reflect.Constructor;

interface MyConnection {
    public void doSomething();
}

class MyConnectionImpl implements MyConnection{
    private String a;
    private String b;

    MyConnectionImpl(String a, String b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void doSomething() {
        System.out.println(a + "_" + b);
    }
}

class MyConnectionFactory {
    public static MyConnection createConnection(String cls, String a, String b){
        Class<?> clazz = null;
        try {
            clazz = Class.forName(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, String.class);
            return (MyConnection)constructor.newInstance(a, b);
        } catch (Exception e) {
            throw new RuntimeException("failed");
        }
    }
}


public class ConnectionFactoryDemo {
    public static void main(String[] args) {
        MyConnection conn = MyConnectionFactory.createConnection("xingoo.MyConnectionImpl", "param1", "param2");
        conn.doSomething();
    }
}
