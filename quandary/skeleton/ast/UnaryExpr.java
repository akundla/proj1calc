package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

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
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl funcDecl) {
        this.expr.staticallyCheck(declaredVars, funcDecl);        
        if (Expr.tryInferType(this.expr, declaredVars) != VAR_TYPE.INT)
            throw new StaticCheckException("Tried to use unary negation on something that is not explicity an int.");
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
