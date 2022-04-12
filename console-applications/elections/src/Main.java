import java.util.Scanner;

public class Main {

    private static final int RICH_CITIZEN_BORDER   = 999_999;
    private static final int NORMAL_CITIZEN_BORDER = 100_000;
    private static final int POOR_CITIZEN_BORDER   = 99;

    public static void main (String[] args) {

        System.out.println("Здравейте, честит празник!");

        final int SERIAL_NUMBER = pickSerialNumber();

        final boolean IS_CITIZEN_RICH   = isCitizenRich(SERIAL_NUMBER);
        final boolean IS_CITIZEN_NORMAL = isCitizenNormal(SERIAL_NUMBER);
        final boolean IS_CITIZEN_POOR   = isCitizenPoor(SERIAL_NUMBER);

        printSerialNumberMessage(
                IS_CITIZEN_RICH,
                IS_CITIZEN_NORMAL,
                IS_CITIZEN_POOR
        );

        final int VOTE_NUMBER = pickVoteNumber();
        printVoteNumberMessage(VOTE_NUMBER);

        boolean isCitizenVip                      = (SERIAL_NUMBER % 2 == 0);
        boolean isBeforeLastDigitGreaterThanThree = (((SERIAL_NUMBER % 100) / 10) > 3);

        boolean isEvadesTaxes = isEvadingTaxes(isBeforeLastDigitGreaterThanThree);

        boolean isCitizenSuperVip = (isCitizenVip && (!isEvadesTaxes && isBeforeLastDigitGreaterThanThree));
        boolean doesCitizenWantTreat = doesCitizenWantTreat(isCitizenSuperVip);



        if (IS_CITIZEN_NORMAL && VOTE_NUMBER == 27 && (!doesCitizenWantTreat && isCitizenSuperVip)) {
            System.out.println("И все пак всеки има право на лош вкус, моля дпозирайте такса от един банан на изхода.");
        }
    }

    private static boolean isCitizenRich(int serialNumber) {
        return serialNumber > RICH_CITIZEN_BORDER;
    }

    private static boolean isCitizenNormal(int serialNumber) {
        return  serialNumber >= NORMAL_CITIZEN_BORDER &&
                serialNumber <= RICH_CITIZEN_BORDER;
    }

    private static boolean isCitizenPoor(int serialNumber) {
        return  serialNumber > POOR_CITIZEN_BORDER &&
                serialNumber < NORMAL_CITIZEN_BORDER;
    }

    private static int pickSerialNumber() {

        Scanner input = new Scanner(System.in);

        System.out.print("Моля въведете номера на вашата персонална идентификационна карта: ");
        int serialNumber = input.nextInt();

        while (serialNumber < POOR_CITIZEN_BORDER) {
            System.out.println("Въведете реален номер.");
            serialNumber = input.nextInt();
        }

        return serialNumber;
    }

    private static void printSerialNumberMessage(
            boolean isRich,
            boolean isNormal,
            boolean isPoor) {

        String message = (isRich)
                ? "Има за нас, има и за вас."
                : (isNormal)
                ? "Радваме се, че нормални хора вече гласуват."
                : (isPoor)
                ? "Хайде по-бързо и да те няма."
                : "Грешка при съобщението на серийния номер!";

        System.out.println(message);
    }

    private static int pickVoteNumber() {

        Scanner input = new Scanner(System.in);

        System.out.println("Кои искате да бъдат вашите бъдещи господари?");
        System.out.println("1    - Партия за прогресивен канибализъм");
        System.out.println("27   - Замундска вегетарианска партия");
        System.out.println("666  - Синдикат на вуду-трактористите");
        System.out.println("999  - Партия на труда, мотиката и наковалнята");

        int voteNumber = input.nextInt();

        while ( voteNumber != 1 && voteNumber != 27 &&
                voteNumber != 666 && voteNumber != 999 ) {

            System.out.println("Въведете от показаните.");
            voteNumber = input.nextInt();
        }

        return voteNumber;
    }

    private static void printVoteNumberMessage(int voteNumber) {

        String message = "Вие си поръчахте да ви управлява ";

        message = switch (voteNumber) {

            case 1   -> message.concat("Партия за прогресивен канибализъм.");
            case 27  -> message.concat("Замундска вегетарианска партия.");
            case 666 -> message.concat("Синдикат на вуду-трактористите.");
            case 999 -> message.concat("Партия на труда, мотиката и наковалнята.");
            default  -> "Грешка при съобщението на номера за гласуване!";
        };

        System.out.println(message);
    }

    private static boolean isEvadingTaxes(boolean isHigher) {

        if (!isHigher) {
            return false;
        }

        Scanner scanner = new Scanner(System.in);
        boolean answer;

        System.out.println("Укривате ли данъци? true/false");
        answer = scanner.nextBoolean();

        String message = (answer) ? "Браво моето момче." : "Будала!";
        System.out.println(message);

        return answer;
    }

    private static boolean doesCitizenWantTreat(boolean isSuperVip) {

        if(!isSuperVip) {
            return false;
        }

        Scanner scanner = new Scanner(System.in);
        boolean answer;

        System.out.println("Колега, искате ли баничка? true/false");
        answer = scanner.nextBoolean();

        String message = (answer) ? "Не може!" : "Ами, то и без това няма.";
        System.out.println(message);

        return answer;
    }
}