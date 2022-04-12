package units;

public class Background extends Units {

    private static int counter = 1;
    private int uniqueNumber = 0;

    public Background() {

        super(Integer.toString(counter));
        this.uniqueNumber = counter++;
    }

    public Background(int valueForUniqueNumber) {

        super(valueForUniqueNumber + "");
        this.uniqueNumber = valueForUniqueNumber;
    }

    public int getUniqueNumberInteger() {
        return this.uniqueNumber;
    }
}