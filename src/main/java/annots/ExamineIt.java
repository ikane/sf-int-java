package annots;

import java.io.FileReader;
import java.lang.reflect.Method;

public class ExamineIt {
    public static void main(String[] args) throws Throwable {
//        System.setSecurityManager(new SecurityManager());
        new FileReader("PrideAndPrejudice.txt");
        ExamineMe em = new ExamineMe();

//        Class<?> cl = ExamineMe.class;
        Class<?> cl = em.getClass();

        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println("Method: " + m);
            RunMe d = m.getAnnotation(RunMe.class);
            if (d != null) {
                System.out.println("RunMe found!!! name is " + d.name() + " value is " + d.value());
                m.setAccessible(true);
                Object rv = m.invoke(em);
            }

        }
    }
}
