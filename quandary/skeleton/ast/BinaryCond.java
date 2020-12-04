package ast;

import java.util.HashMap;
import java.util.List;

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
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.expr1.staticallyCheck(declaredVars, functionDecl);
        this.expr2.staticallyCheck(declaredVars, functionDecl);
    }

    @Override
    boolean eval(HashMap<String, QuandaryValue> variables) {
        switch (this.operator) {
            case BOOL_AND:  return expr1.eval(variables) && expr2.eval(variables);
            case BOOL_OR: return expr1.eval(variables) || expr2.eval(variables);
        }
        throw new RuntimeException("Unexpected in BinaryCond.doOperation");
    }
}
