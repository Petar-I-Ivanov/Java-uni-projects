package processor;

import digits.*;

public class MathCalculator {

    private final char PLUS     = '+';
    private final char MINUS    = '-';
    private final char MULTIPLY = '*';
    private final char DIVIDE   = '/';

    public Digit add(Digit a, Digit b) {
        return sumOfDigits(PLUS, a, b);
    }

    public Digit addMulti(Digit... collection) {
        return sumOfDigits(PLUS, collection);
    }

    public Digit sub(Digit a, Digit b) {
        return sumOfDigits(MINUS, a, b);
    }

    public Digit subMulti(Digit... collection) {
        return sumOfDigits(MINUS, collection);
    }

    public Digit mul(Digit a, Digit b) {
        return sumOfDigits(MULTIPLY, a, b);
    }

    public Digit mulMulti(Digit... collection) {
        return sumOfDigits(MULTIPLY, collection);
    }

    public Digit div(Digit a, Digit b) {
        return sumOfDigits(DIVIDE, a, b);
    }

    public Digit divMulti(Digit... collection) {
        return sumOfDigits(DIVIDE, collection);
    }

    /**
     *
     * Визуализирира числото, във формата към който принадлежи
     * •	BinaryDigit – двоично
     * •	OctDigit - осмично
     * •	DecimalDigit – десетично
     * •	HexDigit – шестнаисетично
     *
     * #Пример:
     * BinaryDigit a = new BinaryDigit(“0101”);
     * MathCalculator calculator = new MathCalculator();
     * calculator.nativeRender(a) // 0101
     * @param digit
     */
    public void renderNative(Digit digit) {
        System.out.println(digit.getValue());
    }

    /**
     * Визуализира числото в двуичен формат
     * @param digit
     */
    public void renderBinary(Digit digit) {
        int decimal = digit.getDecimal();
        String negative = (decimal < 0) ? "-" : "";
        System.out.println(negative + Integer.toBinaryString(Math.abs(decimal)));
    }

    /**
     * Визуализира числото в осмичен формат
     * @param digit
     */
    public void renderOct(Digit digit) {
        int decimal = digit.getDecimal();
        String negative = (decimal < 0) ? "-" : "";
        System.out.println(negative + Integer.toOctalString(Math.abs(decimal)));
    }

    /**
     * Визуализира числото в десетичен формат
     *
     * Пример:
     * BinaryDigit a = new BinaryDigit(“0101”);
     * MathCalculator calculator = new MathCalculator();
     * calculator.decimalRender(a) // 5
     * @param digit
     */
    public void renderDecimal(Digit digit) {
        System.out.println(digit.getDecimal());
    }

    /**
     * Визуализира числото в шестнадесетичен формат
     * @param digit
     */
    public void renderHex(Digit digit) {
        int decimal = digit.getDecimal();
        String negative = (decimal < 0) ? "-" : "";
        System.out.println(negative + Integer.toHexString(Math.abs(decimal)));
    }

    public void nativeRender(Digit digit) {
        renderNative(digit);
    }

    public void binaryRender(Digit digit) {
        renderBinary(digit);
    }

    public void octRender(Digit digit) {
        renderOct(digit);
    }

    public void decimalRender(Digit digit) {
        renderDecimal(digit);
    }

    public void hexRender(Digit digit) {
        renderHex(digit);
    }

    private Digit sumOfDigits (char operation, Digit... collection) {

        int sum = getSum(operation, collection);
        String negative = (sum < 0) ? "-" : "";

        return switch (collection[0].getDigitType()) {
            case BINARY      -> new BinaryDigit(negative + Integer.toBinaryString(Math.abs(sum)));
            case OCTAL       -> new OctDigit(negative + Integer.toOctalString(Math.abs(sum)));
            case DECIMAL     -> new DecimalDigit(negative + (Math.abs(sum)));
            case HEXADECIMAL -> new HexDigit(negative + Integer.toHexString(Math.abs(sum)));
        };
    }

    private int getSum(char operation, Digit... collection) {

        int sum = 0;

        for (Digit d : collection) {

            if (d.equals(collection[0])) {
                sum = d.getDecimal();
                continue;
            }

            sum =   (operation == PLUS)     ? sum + d.getDecimal() :
                    (operation == MINUS)    ? sum - d.getDecimal() :
                    (operation == MULTIPLY) ? sum * d.getDecimal() :
                    (operation == DIVIDE)   ? sum / d.getDecimal() :
                    0;
        }

        return sum;
    }
}