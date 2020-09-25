package ast;

public class UnaryExpr extends Expr {

    public static final int UNARY_NEGATION = 1;
    public static final int BOOLEAN_NOT = 2;

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
            case BOOLEAN_NOT: s = "!"; break;
        }
        return s + " " + expr;
    }

    @Override
    Object eval() {
        return doOperation(operator, expr.eval());
    }

    static Object doOperation(int operator, Object value) {
        switch (operator) {
            case UNARY_NEGATION:  return -(long)value;
            case BOOLEAN_NOT: return !(boolean)value;
        }
        throw new RuntimeException("Unexpected in UnaryExpr.doOperation");
    }
}
