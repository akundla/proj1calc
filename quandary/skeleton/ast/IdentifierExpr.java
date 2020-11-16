package ast;

import java.util.HashMap;
import java.util.List;

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
    public void staticallyCheck(List<VarDecl> declaredVars) {
        /**
         * Nothing to check here. Vars can either be declared or accessed but never both. Which one depends on whether it is already declared. 
         * One is relevant for declaration and the other for assignment. Thus, what you check about an identifier depends on how it is used.
         */
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        return variables.get(this.identifier);
    }
}
