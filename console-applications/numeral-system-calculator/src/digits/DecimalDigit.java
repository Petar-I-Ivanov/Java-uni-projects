package digits;

public class DecimalDigit extends Digit {

    public DecimalDigit(String digit) {
        super(DigitTypeEnum.DECIMAL, digit);
    }

    public int getDecimal() {
        return Integer.parseInt(this.value);
    }
}