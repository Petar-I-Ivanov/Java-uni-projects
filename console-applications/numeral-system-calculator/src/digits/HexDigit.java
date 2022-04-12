package digits;

public class HexDigit extends Digit {

    public HexDigit(String digit) {
        super(DigitTypeEnum.HEXADECIMAL, digit);
    }

    public int getDecimal() {
        return Integer.parseInt(this.value, 16);
    }
}