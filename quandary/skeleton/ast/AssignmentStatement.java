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
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.ident, this.rValue.eval(variables));
        return null;
    }
}
