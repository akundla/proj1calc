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

    /**
     * If this was called then the return value is meant to be ignored, so ret null.
     */
    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        funcCallExpr.eval(variables);
        return null;
    }
}
