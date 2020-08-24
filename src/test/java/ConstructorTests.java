import com.nesaak.noreflection.NoReflection;
import com.nesaak.noreflection.access.DynamicCaller;
import org.junit.jupiter.api.Test;

public class ConstructorTests {

    @Test
    public void testNoParam() throws NoSuchMethodException {
        DynamicCaller caller = NoReflection.shared().get(ConstructorTest.class.getDeclaredConstructor());
        ConstructorTest obj = (ConstructorTest) caller.call();
        assert(obj.getValue().equals(500));
    }

    @Test
    public void testOneParam() throws NoSuchMethodException {
        DynamicCaller caller = NoReflection.shared().get(ConstructorTest.class.getDeclaredConstructor(Integer.class));
        ConstructorTest obj = (ConstructorTest) caller.call(Integer.MAX_VALUE);
        assert(obj.getValue().equals(Integer.MAX_VALUE));
    }

    @Test
    public void testPrimitives() throws NoSuchMethodException {
        DynamicCaller caller = NoReflection.shared().get(ConstructorTest.class.getDeclaredConstructor(int.class, byte.class, Integer.class));
        ConstructorTest obj = (ConstructorTest) caller.call(500, (byte) 100, 150);
        assert(obj.getValue().equals(750));
    }

    @Test
    public void testSystemConstructor() throws NoSuchMethodException {
        DynamicCaller caller = NoReflection.shared().get(String.class.getDeclaredConstructor(byte[].class));
        String str = "testing";
        assert(str.equals(caller.call((Object) str.getBytes())));
    }

}
