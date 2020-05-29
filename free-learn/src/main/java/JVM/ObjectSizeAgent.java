package JVM;

import java.lang.instrument.Instrumentation;

/**
 * @author lpx .
 * @create 2020-04-20-17:36 .
 * @description .
 */
public class ObjectSizeAgent {

    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation _inst) {
        inst = _inst;
    }

    public static long sizeOf(Object o) {
        return inst.getObjectSize(o);
    }
}
