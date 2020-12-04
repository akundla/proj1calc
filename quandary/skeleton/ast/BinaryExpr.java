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
                
            long left, right;
            left = ((QuandaryIntValue)expr1.eval(variables)).value;
            right = ((QuandaryIntValue)expr2.eval(variables)).value;

            return new QuandaryIntValue(
                doOperation(
                    left,
                    this.op,
                    right
                )
            );
        }
        else if (this.op == OPERATOR.DOT) {

            QuandaryValue left, right;
            left = this.expr1.eval(variables);
            right = this.expr2.eval(variables);

            return new QuandaryRefValue(
                new QuandaryObject(left, right)
            );
        }
        else {
            throw new RuntimeException("Operator not recognized.");
        }
    }

    public QuandaryValue evalConcurrently(HashMap<String, QuandaryValue> variables) {
        if (this.op == OPERATOR.PLUS
            || this.op == OPERATOR.MINUS
            || this.op == OPERATOR.MULT) {
                
            long left, right;
            // TODO: Implement concurrency
            left = ((QuandaryIntValue)expr1.eval(variables)).value;
            right = ((QuandaryIntValue)expr2.eval(variables)).value;

            return new QuandaryIntValue(
                doOperation(
                    left,
                    this.op,
                    right
                )
            );
        }
        else if (this.op == OPERATOR.DOT) {

            QuandaryValue left, right;
            // TODO: Implement concurrency
            left = this.expr1.eval(variables);
            right = this.expr2.eval(variables);

            return new QuandaryRefValue(
                new QuandaryObject(left, right)
            );
        }
        else {
            throw new RuntimeException("Operator not recognized.");
        }
    }

    static Long doOperation(long left, OPERATOR operator, long right) {
        switch (operator) {
            case PLUS:  return left + right;
            case MINUS: return left - right;
            case MULT: return left * right;
        }
        throw new RuntimeException("Unexpected in BinaryExpr.doOperation");
    }
}
