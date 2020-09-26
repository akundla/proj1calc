package ast;

public class BinaryExprCond extends Cond {

    public static final int LESS_THAN = 1;
    public static final int LESS_THAN_OR_EQUAL_TO = 2;
    public static final int GREATER_THAN = 3;
    public static final int GREATER_THAN_OR_EQUAL_TO = 4;
    public static final int EQUALITY_CHECK = 5;
    public static final int NON_EQUALITY_CHECK = 6;

    final Expr expr1;
    final int operator;
    final Expr expr2;

    public BinaryExprCond(Expr expr1, int operator, Expr expr2, Location loc) {
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
            case LESS_THAN:  s = "<"; break;
            case LESS_THAN_OR_EQUAL_TO: s = "<"; break;
            case GREATER_THAN: s = ">"; break;
            case GREATER_THAN_OR_EQUAL_TO: s = ">="; break;
            case EQUALITY_CHECK: s = "=="; break;
            case NON_EQUALITY_CHECK: s = "!="; break;
        }
        return expr1 + " " + s + " " + expr2;
    }

    @Override
    boolean eval() {
        // Expressions evaluate only to longs thus they can be cast
        return doOperation((long)expr1.eval(), operator, (long)expr2.eval());
    }

    static boolean doOperation(long value1, int operator, long value2) {
        switch (operator) {
            case LESS_THAN:  return value1 < value2;
            case LESS_THAN_OR_EQUAL_TO: return value1 <= value2;
            case GREATER_THAN: return value1 > value2;
            case GREATER_THAN_OR_EQUAL_TO: return value1 >= value2;
            case EQUALITY_CHECK: return value1 == value2;
            case NON_EQUALITY_CHECK: return value1 != value2;
        }
        throw new RuntimeException("Unexpected in BinaryExpr.doOperation");
    }
}