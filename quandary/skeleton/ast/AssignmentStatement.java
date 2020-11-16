package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class AssignmentStatement extends Statement {
    
    final String ident;
    final Expr rValue;

    public AssignmentStatement(String ident, Expr expr, Location loc) {
        super(loc);
        this.ident = ident;
        this.rValue = expr;
    }

    @Override
    public String toString() {
        return this.ident + " = " + this.rValue;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.rValue.staticallyCheck(declaredVars);
        // TODO: Finish checking this
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.ident, this.rValue.eval(variables));
        return null;
    }
}
