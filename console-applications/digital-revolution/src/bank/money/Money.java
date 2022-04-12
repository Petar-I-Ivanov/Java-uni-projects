package bank.money;

public abstract class Money {

    protected String code;
    protected double value;

    protected Money (String code, double value) {
        this.code  = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public double getValue() {
        return this.value;
    }

    public void addValue(double value) {
        this.value += value;
    }

    public void subValue(double value) {
        this.value -= value;
    }

    public static double converter (Money fromMoney, String toMoney) {

        double apollo =   (fromMoney.getCode().equals("USD")) ? 10
                        : (fromMoney.getCode().equals("CAD")) ? 5
                        : (fromMoney.getCode().equals("JAP")) ? 7
                        : (fromMoney.getCode().equals("CHF")) ? 20

                        : (fromMoney.getCode().equals("GLD")) ? 1000

                        : (fromMoney.getCode().equals("BTC")) ? 1054
                        : (fromMoney.getCode().equals("SBB")) ? 55
                        : (fromMoney.getCode().equals("RTA")) ? 0.5
                        : (fromMoney.getCode().equals("QAQ")) ? 0.014

                        : 1;

        apollo *= fromMoney.getValue();

        apollo /= (toMoney.equals("USD")) ? 10
                : (toMoney.equals("CAD")) ? 5
                : (toMoney.equals("JAP")) ? 7
                : (toMoney.equals("CHF")) ? 20

                : (toMoney.equals("GLD")) ? 1000

                : (toMoney.equals("BTC")) ? 1054
                : (toMoney.equals("SBB")) ? 55
                : (toMoney.equals("RTA")) ? 0.5
                : (toMoney.equals("QAQ")) ? 0.014

                : 1;

        return apollo;
    }
}