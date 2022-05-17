package it.isa.test;

public class Operation {
    private ServerInterface si;
    int operationsCounter;

    public Operation() {
        operationsCounter = 0;
    }

    public Operation(int operationsCounter) {
        this.operationsCounter = operationsCounter;
    }

    public Operation(ServerInterface si) {
        this.si = si;
    }

    public int getServerStatus() {
        return si.getStatus();
    }

    public String getUrl(String serviceName, String state) {
        return si.getUrl(serviceName, state);
    }

    public float sum(float a, float b) {
        operationsCounter++;
        return a + b;
    }

    // qui il test fallisce con quickCheck perchè può andare in overflow
    public int sumInt(int a, int b) {

        long res = (long) a + b;

        if (res > java.lang.Integer.MAX_VALUE) {
            // overflow
        }

        operationsCounter++;
        return a + b;
    }

    public float divisione(int a, int b) {
        return (float) a / b;
    }

    public int getCount() {
        return operationsCounter;
    }

    public boolean positivi(int a, int b) {
        if (a > 0 && b > 0)
            return true;
        else
            return false;
    }
}
