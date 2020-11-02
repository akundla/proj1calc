package ast;

import java.util.HashMap;

public class MutFuncCallStatement extends Statement {
    
    final FunctionCallExpr funcCallExpr;

    public MutFuncCallStatement(FunctionCallExpr funcCallExpr, Location loc) {
        super(loc);
        this.funcCallExpr = funcCallExpr;
    }

    @Override
    public String toString() {
        return this.funcCallExpr.toString();
    }

    @Override
    Object exec(HashMap<String, QuandaryValue> variables) {
        funcCallExpr.eval(variables);
        return null;
    }
}
