import com.nesaak.noreflection.NoReflection;
import com.nesaak.noreflection.access.DynamicCaller;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class MethodTests implements Supplier {

    @Test
    public void testInterface() throws NoSuchMethodException {
        DynamicCaller interfaceAccess = NoReflection.shared().get(Supplier.class.getDeclaredMethod("get"));
        DynamicCaller classAccess = NoReflection.shared().get(getClass().getDeclaredMethod("get"));
        assert(classAccess.call(this).equals(interfaceAccess.call(this)));
    }

    @Override
    public Object get() {
        return Integer.MAX_VALUE;
    }

    @Test
    public void testStatic() throws NoSuchMethodException {
        DynamicCaller methodAccess = NoReflection.shared().get(getClass().getDeclaredMethod("testStaticCall"));
        assert(methodAccess.call(this).equals(Long.MAX_VALUE));
    }

    private static Long testStaticCall() {
        return Long.MAX_VALUE;
    }

    @Test
    public void testParameters() throws NoSuchMethodException {
        DynamicCaller methodAccess = NoReflection.shared().get(getClass().getDeclaredMethod("testParametersCall", Integer.class, Integer.class));
        assert(methodAccess.call(this, 50, 87).equals(50 * 87));
    }

    private Integer testParametersCall(Integer one, Integer two) {
        return one * two;
    }

    @Test
    public void testPrimitives() throws NoSuchMethodException {
        DynamicCaller methodAccess = NoReflection.shared().get(getClass().getDeclaredMethod("testPrimitivesCall", int.class, Integer.class, int.class));
        assert(methodAccess.call(this, 50, 87, 90).equals((50 * 87) + 90));
    }

    private int testPrimitivesCall(int one, Integer two, int three) {
        return (one * two) + three;
    }

    @Test
    public void testVoid() throws NoSuchMethodException {
        DynamicCaller methodAccess = NoReflection.shared().get(getClass().getDeclaredMethod("testVoidCall", StringBuilder.class));
        StringBuilder builder = new StringBuilder();
        methodAccess.call(this, builder);
        assert(builder.toString().equals("test"));
    }

    private void testVoidCall(StringBuilder builder) {
        builder.append("test");
    }

    @Test
    public void testSystemMethod() throws NoSuchMethodException {
        DynamicCaller methodAccess = NoReflection.shared().get(String.class.getDeclaredMethod("indexOfNonWhitespace"));
        assert(methodAccess.call(" s").equals(1));
    }

}
