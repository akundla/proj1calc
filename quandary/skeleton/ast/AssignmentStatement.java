package ast;

import java.util.HashMap;

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
    Object exec(HashMap<String, Long> variables) {
        variables.put(this.ident, (long)this.rValue.eval(variables));
        return null;
    }
}
