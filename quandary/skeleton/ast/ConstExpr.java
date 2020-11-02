package ast;

import java.util.HashMap;

public class ConstExpr extends Expr {
    final Object value;

    public ConstExpr(long value, Location loc) {
        super(loc);
        this.value = value;
    }

    public ConstExpr(Location loc) {
        super(loc);
        this.value = null;
    }

    @Override
    public String toString() {
        if (this.value != null)
            return value.toString();
        else
            return "nil";
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        if (this.value instanceof Long) {
            return new QuandaryIntValue((long)this.value);
        }
        else if (this.value == null) {
            // Ref of value nil is just a ref with a null reference
            return new QuandaryRefValue(null);
        }
        throw new RuntimeException("ConstExpr recieved something that isn't a long and isn't a representation of nil.");
    }
}
