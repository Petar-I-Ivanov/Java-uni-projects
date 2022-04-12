package digits;

public class BinaryDigit extends Digit {

    public BinaryDigit(String digit) {
        super(DigitTypeEnum.BINARY, digit);
    }

    public int getDecimal() {
        return Integer.parseInt(this.value, 2);
    }
}