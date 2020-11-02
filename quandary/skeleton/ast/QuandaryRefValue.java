package ast;

public class QuandaryRefValue extends QuandaryValue {
    // If this is null, then it represents the value nil;
    public QuandaryObject referencedQObject;

    public QuandaryRefValue(QuandaryObject qObj) {
        this.referencedQObject = qObj;
    }

    @Override
    public String toString() {
        if (this.referencedQObject == null) {
            return "nil";
        }
        else {
            return referencedQObject.toString();
        }
    }
}
