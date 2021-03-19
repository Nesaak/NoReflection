import com.nesaak.noreflection.NoReflection;
import com.nesaak.noreflection.access.FieldAccess;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FieldTests {

    private boolean booleanTest;
    private char characterTest;
    private byte byteTest;
    private short shortTest;
    private int integerTest;
    private float floatTest;
    private long longTest;
    private double doubleTest;

    private Object objectTest;
    private Collection<?> genericTest1;
    private List<String> genericTest2;

    private int[] primitiveArrayTest;
    private Object[] objectArrayTest;
    private int[][] primitiveMultiArrayTest;
    private Object[][] objectMultiArrayTest;

    private static Object staticTest;

    private final Object finalTest = new Object();
    private static final Object finalStaticTest = new Object();

    @Test
    public void testBoolean() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("booleanTest"));
        fieldAccess.set(this, true);
        assert((boolean) fieldAccess.get(this));
    }

    @Test
    public void testCharacter() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("characterTest"));
        fieldAccess.set(this, Character.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Character.MAX_VALUE));
    }

    @Test
    public void testByte() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("byteTest"));
        fieldAccess.set(this, Byte.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Byte.MAX_VALUE));
    }

    @Test
    public void testShort() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("shortTest"));
        fieldAccess.set(this, Short.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Short.MAX_VALUE));
    }

    @Test
    public void testInteger() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("integerTest"));
        fieldAccess.set(this, Integer.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Integer.MAX_VALUE));
    }

    @Test
    public void testFloat() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("floatTest"));
        fieldAccess.set(this, Float.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Float.MAX_VALUE));
    }

    @Test
    public void testLong() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("longTest"));
        fieldAccess.set(this, Long.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Long.MAX_VALUE));
    }

    @Test
    public void testDouble() throws NoSuchFieldException {
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("doubleTest"));
        fieldAccess.set(this, Double.MAX_VALUE);
        assert(fieldAccess.get(this).equals(Double.MAX_VALUE));
    }

    @Test
    public void testObject() throws NoSuchFieldException {
        Object object = new Object();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("objectTest"));
        fieldAccess.set(this, object);
        assert(fieldAccess.get(this).equals(object));
    }

    @Test
    public void genericTest1() throws NoSuchFieldException {
        Collection<Object> collection = Collections.emptyList();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("genericTest1"));
        fieldAccess.set(this, collection);
        assert(fieldAccess.get(this) == collection);
    }

    @Test
    public void genericTest2() throws NoSuchFieldException {
        List<String> list = new ArrayList<>();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("genericTest2"));
        fieldAccess.set(this, list);
        assert(fieldAccess.get(this).equals(list));
    }

    @Test
    public void primitiveArrayTest() throws NoSuchFieldException {
        int[] primitiveArray = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("primitiveArrayTest"));
        fieldAccess.set(this, primitiveArray);
        assert(fieldAccess.get(this).equals(primitiveArray));
    }

    @Test
    public void objectArrayTest() throws NoSuchFieldException {
        Object[] objectArray = new Object[] {new Object()};
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("objectArrayTest"));
        fieldAccess.set(this, objectArray);
        assert(fieldAccess.get(this).equals(objectArray));
    }

    @Test
    public void primitiveMultiArrayTest() throws NoSuchFieldException {
        int[][] primitiveMultiArray = new int[][] {};
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("primitiveMultiArrayTest"));
        fieldAccess.set(this, primitiveMultiArray);
        assert(fieldAccess.get(this).equals(primitiveMultiArray));
    }

    @Test
    public void objectMultiArrayTest() throws NoSuchFieldException {
        Object[][] objectMultiArray = new Object[][] {};
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("objectMultiArrayTest"));
        fieldAccess.set(this, objectMultiArray);
        assert(fieldAccess.get(this).equals(objectMultiArray));
    }

    @Test
    public void staticTest() throws NoSuchFieldException {
        Object object = new Object();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("staticTest"));
        fieldAccess.set(this, object);
        assert(fieldAccess.get(this).equals(object));
    }

    @Test
    public void finalTest() throws NoSuchFieldException {
        Object object = new Object();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("finalTest"));
        fieldAccess.set(this, object);
        assert(fieldAccess.get(this).equals(object));
    }

    @Test
    public void finalStaticTest() throws NoSuchFieldException {
        Object object = new Object();
        FieldAccess fieldAccess = NoReflection.shared().get(getClass().getDeclaredField("finalStaticTest"));
        fieldAccess.set(this, object);
        assert(fieldAccess.get(this).equals(object));
    }

    @Test
    public void testSystemField() throws NoSuchFieldException {
        String string = "test";
        FieldAccess fieldAccess = NoReflection.shared().get(String.class.getDeclaredField("hash"));
        fieldAccess.set(string, Integer.MAX_VALUE);
        assert(fieldAccess.get(string).equals(Integer.MAX_VALUE));
    }

}
