package tests;

import digits.Digit;
import digits.BinaryDigit;
import digits.DecimalDigit;
import processor.MathCalculator;

public class MixOperationTest {

    public static void test(MathCalculator calculator) {

        Digit a  = new BinaryDigit("1101");
        Digit b  = new DecimalDigit("10574");

        Digit addResult = calculator.add(a, b);
        calculator.renderNative(addResult);
        calculator.renderBinary(addResult);

        Digit subResult = calculator.sub(a, b);
        calculator.renderNative(subResult);
        calculator.renderDecimal(subResult);

        Digit mulResult = calculator.mul(a, b);
        calculator.renderNative(mulResult);
        calculator.renderOct(mulResult);

        Digit divResult = calculator.div(a, b);
        calculator.renderNative(divResult);
        calculator.renderHex(divResult);
    }
}