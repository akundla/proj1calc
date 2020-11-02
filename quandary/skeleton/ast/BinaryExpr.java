package ast;

import java.util.HashMap;

public class BinaryExpr extends Expr {

    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int MULT = 3;

    final Expr expr1;
    final int operator;
    final Expr expr2;

    public BinaryExpr(Expr expr1, int operator, Expr expr2, Location loc) {
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
            case PLUS:  s = "+"; break;
            case MINUS: s = "-"; break;
            case MULT: s = "*"; break;
        }
        return expr1 + " " + s + " " + expr2;
    }

    @Override
    Object eval(HashMap<String, QuandaryValue> variables) {
        return doOperation(expr1.eval(variables), operator, expr2.eval(variables));
    }

    static Object doOperation(Object value1, int operator, Object value2) {
        switch (operator) {
            case PLUS:  return (long)value1 + (long)value2;
            case MINUS: return (long)value1 - (long)value2;
            case MULT: return (long)value1 * (long)value2;
        }
        throw new RuntimeException("Unexpected in BinaryExpr.doOperation");
    }
}
