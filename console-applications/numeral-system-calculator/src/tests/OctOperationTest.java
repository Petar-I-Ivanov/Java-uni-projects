package tests;

import digits.Digit;
import digits.OctDigit;
import processor.MathCalculator;

public class OctOperationTest {

    public static void test(MathCalculator calculator) {

        Digit a = new OctDigit("531");
        Digit b = new OctDigit("67");

        Digit addResult = calculator.add(a, b);
        calculator.nativeRender(addResult);
        calculator.binaryRender(addResult);

        Digit subResult = calculator.sub(a, b);
        calculator.nativeRender(subResult);
        calculator.octRender(subResult);

        Digit mulResult = calculator.mul(a, b);
        calculator.nativeRender(mulResult);
        calculator.decimalRender(mulResult);

        Digit divResult = calculator.div(a, b);
        calculator.nativeRender(divResult);
        calculator.hexRender(divResult);
    }
}