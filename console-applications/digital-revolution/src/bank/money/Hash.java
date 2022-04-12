package bank.money;

public class Hash extends Money {

    private boolean isStraight;

    public Hash(String code, double value) {
        super(code, value);
        this.isStraight = code.equals("BTC") || code.equals("SBB");
    }

    public boolean getDirection() {
        return this.isStraight;
    }
}