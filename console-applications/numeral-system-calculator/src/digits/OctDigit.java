package digits;

public class OctDigit extends Digit {

    public OctDigit(String digit) {
        super(DigitTypeEnum.OCTAL, digit);
    }

    public int getDecimal() {
        return Integer.parseInt(this.value, 8);
    }
}