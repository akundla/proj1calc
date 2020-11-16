package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class MutFuncCallStatement extends Statement {
    
    final FunctionCallExpr funcCallExpr;

    public MutFuncCallStatement(FunctionCallExpr funcCallExpr, Location loc) {
        super(loc);
        this.funcCallExpr = funcCallExpr;
    }

    @Override
    public String toString() {
        return this.funcCallExpr.toString() + ";";
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.funcCallExpr.staticallyCheck(declaredVars);
        // TODO: Finish checking this
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
