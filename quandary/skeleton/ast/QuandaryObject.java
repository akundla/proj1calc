package ast;

public class QuandaryObject extends QuandaryValue {
    public QuandaryValue left;
    public QuandaryValue right;

    public QuandaryObject(QuandaryValue left, QuandaryValue right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " . " + this.right.toString() + ")";
    }
}
