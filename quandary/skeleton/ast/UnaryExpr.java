package ast;

import java.util.HashMap;

public class UnaryExpr extends Expr {

    public static final int UNARY_NEGATION = 1;

    final int operator;
    final Expr expr;

    public UnaryExpr(int operator, Expr expr, Location loc) {
        super(loc);
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "(" + simpleString() + ")";
    }

    public String simpleString() {
        String s = null;
        switch (operator) {
            case UNARY_NEGATION:  s = "-"; break;
        }
        return s + " " + expr;
    }

    @Override
    Object eval(HashMap<String, Long> variables) {
        return doOperation(operator, expr.eval(variables));
    }

    static Object doOperation(int operator, Object value) {
        switch (operator) {
            case UNARY_NEGATION:  return -(long)value;
        }
        throw new RuntimeException("Unexpected in UnaryExpr.doOperation");
    }
}
