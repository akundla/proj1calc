package ast;

import java.util.HashMap;

public class ReturnStatement extends Statement {

    final Expr expr;
    
    public ReturnStatement(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return " + this.expr.toString() + ";";
    }

    /**
     * Here, the one statement that actually returns something! It is of course a quandaryValue
     */
    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        return this.expr.eval(variables);
    }
}
