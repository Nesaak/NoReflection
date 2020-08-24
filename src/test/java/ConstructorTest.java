public class ConstructorTest {

    private Integer value;

    private ConstructorTest() {
        value = 500;
    }

    public ConstructorTest(Integer integer) {
        value = integer;
    }

    public ConstructorTest(int integer, byte byteValue, Integer secondInteger) {
        value = integer + byteValue + secondInteger;
    }

    public Integer getValue() {
        return value;
    }
}
