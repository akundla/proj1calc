package ast;

import java.util.HashMap;

public class ConstExpr extends Expr {
    // Predefined constant representation of nil
    public static final String NIL = "nil";

    final Object value;

    public ConstExpr(long value, Location loc) {
        super(loc);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        if (this.value instanceof Long) {
            return new QuandaryIntValue((long)this.value);
        }
        else if (this.value instanceof String && NIL.equals((String)this.value)) {
            // Ref of value nil is just a ref with a null reference
            return new QuandaryRefValue(null);
        }
        throw new RuntimeException("ConstExpr recieved something that isn't a long and isn't a representation of nil.");
    }
}
