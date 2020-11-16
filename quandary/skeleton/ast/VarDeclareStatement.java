package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class VarDeclareStatement extends Statement {
    
    final VarDecl varDecl;
    final Expr rValue;

    public VarDeclareStatement(VarDecl varDecl, Expr expr, Location loc) {
        super(loc);
        this.varDecl = varDecl;
        this.rValue = expr;
    }

    @Override
    public String toString() {
        return this.varDecl + " = " + this.rValue;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {

        // One-time pass through the declared variables to ensure that the identifier is not already used
        for (int i = 0; i < declaredVars.size(); i++){
            if (this.varDecl.identifier == declaredVars.get(i).identifier)
                throw new StaticCheckException("Identifier " + this.varDecl.identifier + " has already been used.");
        }
        // Add it to the identifiers so it can be seen and not redeclared
        declaredVars.add(this.varDecl);

        this.rValue.staticallyCheck(declaredVars);
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.varDecl.identifier, (QuandaryValue)this.rValue.eval(variables));
        return null;
    }
}
