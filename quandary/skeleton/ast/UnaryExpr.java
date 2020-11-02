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
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        return new QuandaryIntValue(
            doOperation(
                operator,
                // If this is not a QuandaryIntValue then the program is statically incorrect
                ((QuandaryIntValue)expr.eval(variables)).value
            )
        );
    }

    static Long doOperation(int operator, Long value) {
        switch (operator) {
            case UNARY_NEGATION:  return -value;
        }
        throw new RuntimeException("Unexpected in UnaryExpr.doOperation");
    }
}
