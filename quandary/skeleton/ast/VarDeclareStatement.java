package ast;

import java.util.HashMap;

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
        return "int " + this.varDecl.identifier + " = " + this.rValue;
    }

    @Override
    Object exec(HashMap<String, Long> variables) {
        variables.put(this.varDecl.identifier, (long)this.rValue.eval(variables));
        return null;
    }
}
