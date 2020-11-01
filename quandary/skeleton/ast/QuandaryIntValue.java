package ast;

public class QuandaryIntValue extends QuandaryValue {
    public long value;

    public QuandaryIntValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Long.toString(this.value);
    }
}
