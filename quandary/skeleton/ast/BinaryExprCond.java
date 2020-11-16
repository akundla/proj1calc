package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

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
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.expr1.staticallyCheck(declaredVars, functionDecl);
        this.expr2.staticallyCheck(declaredVars, functionDecl);

        if (Expr.tryInferType(this.expr1, declaredVars) != VAR_TYPE.INT || Expr.tryInferType(this.expr2, declaredVars) != VAR_TYPE.INT)
            throw new StaticCheckException("One or both of the experssions in Binary Expression condition was not an int.");
    }

    @Override
    boolean eval(HashMap<String, QuandaryValue> variables) {
        // Expressions evaluate only to longs thus they can be cast
        return doOperation(
            ((QuandaryIntValue)expr1.eval(variables)).value,
            operator,
            ((QuandaryIntValue)expr2.eval(variables)).value
        );
    }

    // From the lang spec: All of the ops are on longs only
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
