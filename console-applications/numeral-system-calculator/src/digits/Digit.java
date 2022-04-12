package digits;

public abstract class Digit {

    protected DigitTypeEnum digitType;
    protected String value;

    public Digit (DigitTypeEnum digitType, String digit) {
        this.digitType = digitType;
        this.value = digit;
    }

    public DigitTypeEnum getDigitType() { return this.digitType; }

    public String getValue() { return this.value; }

    public abstract int getDecimal();
}