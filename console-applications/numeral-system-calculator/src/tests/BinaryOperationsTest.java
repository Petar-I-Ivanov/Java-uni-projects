package tests;

import digits.Digit;
import digits.BinaryDigit;
import processor.MathCalculator;

public class BinaryOperationsTest {

    public static void test(MathCalculator calculator) {

        BinaryDigit a = new BinaryDigit("1101");
        BinaryDigit b = new BinaryDigit("0001");

        Digit addResult = calculator.add(a, b);
        calculator.renderNative(addResult);
        calculator.renderDecimal(addResult);

        Digit subResult = calculator.sub(a, b);
        calculator.renderNative(subResult);
        calculator.renderDecimal(subResult);

        Digit mulResult = calculator.mul(a, b);
        calculator.renderNative(mulResult);
        calculator.renderDecimal(mulResult);

        Digit divResult = calculator.div(a, b);
        calculator.renderNative(divResult);
        calculator.renderDecimal(divResult);
    }
}