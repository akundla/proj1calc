package ast;

import java.util.HashMap;

public class IdentifierExpr extends Expr {

    final String identifier;

    public IdentifierExpr(String identifier, Location loc) {
        super(loc);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        return variables.get(this.identifier);
    }
}
