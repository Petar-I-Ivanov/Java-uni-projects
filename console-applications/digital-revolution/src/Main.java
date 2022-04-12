import tests.ConvertTest;
import tests.TransferTest;

public class Main {

    public static void main(String[] args) {

        System.out.println("Test one (manual)");
        ConvertTest.test();

        System.out.println("Test two (auto)");
        TransferTest.test();
    }
}