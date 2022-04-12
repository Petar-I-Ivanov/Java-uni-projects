import processor.MathCalculator;
import tests.*;

public class Application {

    public static void main(String[] args) {

        MathCalculator calculator = new MathCalculator();

//      Тест 1 - Операции с бинарни числа
//      ==================================================
        System.out.println("Test 1");
        BinaryOperationsTest.test(calculator);

//      Тест 2 - Операции с осмични числа
//      ==================================================
        System.out.println("Test 2");
        OctOperationTest.test(calculator);

//      Тест 3 - Операции с десетични числа
//      ==================================================
        System.out.println("Test 3");
        DecimalOperationTest.test(calculator);

//      Тест 4 - Операции с шестнаисетични числа
//      ==================================================
        System.out.println("Test 4");
        HexOperationTest.test(calculator);

//      Тест 5 - Операции с числа в различни формати
//      ==================================================
        System.out.println("Test 5");
        MixOperationTest.test(calculator);

//      Тест 6 - Операции с много числа
//      ==================================================
        System.out.println("Test 6");
        MultiOperationTest.test(calculator);
    }
}