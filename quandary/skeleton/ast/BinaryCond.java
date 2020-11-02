package ast;

import java.util.HashMap;

public class BinaryCond extends Cond {

    public static final int BOOL_AND = 7;
    public static final int BOOL_OR = 8;

    final Cond expr1;
    final int operator;
    final Cond expr2;

    public BinaryCond(Cond expr1, int operator, Cond expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return "(" + simpleString() + ")";
    }

    public String simpleString() {
        String s = null;
        switch (operator) {
            case BOOL_AND:  s = "&&"; break;
            case BOOL_OR: s = "||"; break;
        }
        return expr1 + " " + s + " " + expr2;
    }

    @Override
    boolean eval(HashMap<String, QuandaryValue> variables) {
        return doOperation(expr1.eval(variables), operator, expr2.eval(variables));
    }

    static boolean doOperation(boolean value1, int operator, boolean value2) {
        switch (operator) {
            case BOOL_AND:  return value1 && value2;
            case BOOL_OR: return value1 || value2;
        }
        throw new RuntimeException("Unexpected in BinaryCond.doOperation");
    }
}
