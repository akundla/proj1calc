package ast;

import java.util.HashMap;
import java.util.List;

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

    public void staticallyCheck(List<VarDecl> declaredVars) {
        for (int i = 0; i < declaredVars.size(); i++){
            if (this.varDecl.identifier == declaredVars.get(i).identifier)
                throw new StaticCheckException("Identifier " + this.varDecl.identifier + " has already been used.");
        }
        declaredVars.add(this.varDecl);
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.varDecl.identifier, (QuandaryValue)this.rValue.eval(variables));
        return null;
    }
}
