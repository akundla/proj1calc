package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class BinaryExpr extends Expr {

    public static enum OPERATOR {
        PLUS, MINUS, MULT, DOT
    };

    final Expr expr1;
    final OPERATOR op;
    final Expr expr2;

    public BinaryExpr(Expr expr1, OPERATOR operator, Expr expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.op = operator;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return "(" + simpleString() + ")";
    }

    public String simpleString() {
        String s = null;
        switch (this.op) {
            case PLUS:  s = "+"; break;
            case MINUS: s = "-"; break;
            case MULT: s = "*"; break;
            case DOT: s = "."; break;
        }
        return expr1 + " " + s + " " + expr2;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.expr1.staticallyCheck(declaredVars, functionDecl);
        this.expr2.staticallyCheck(declaredVars, functionDecl);

        if (
            (this.op == OPERATOR.PLUS || this.op == OPERATOR.MINUS || this.op == OPERATOR.MULT)
            && (
                Expr.tryInferType(this.expr1, declaredVars) != VAR_TYPE.INT || 
                Expr.tryInferType(this.expr2, declaredVars) != VAR_TYPE.INT
                )
            )
            throw new StaticCheckException("Operands to arithmetic binary expression were not ints.");
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        if (this.op == OPERATOR.PLUS
            || this.op == OPERATOR.MINUS
            || this.op == OPERATOR.MULT) {
            return new QuandaryIntValue(
                doOperation(
                    ((QuandaryIntValue)expr1.eval(variables)).value,
                    this.op,
                    ((QuandaryIntValue)expr2.eval(variables)).value
                )
            );
        }
        else if (this.op == OPERATOR.DOT) {
            return new QuandaryRefValue(
                new QuandaryObject(this.expr1.eval(variables), this.expr2.eval(variables))
            );
        }
        else {
            throw new RuntimeException("Operator not recognized.");
        }
    }

    static Long doOperation(long value1, OPERATOR operator, long value2) {
        switch (operator) {
            case PLUS:  return value1 + value2;
            case MINUS: return value1 - value2;
            case MULT: return value1 * value2;
        }
        throw new RuntimeException("Unexpected in BinaryExpr.doOperation");
    }
}
