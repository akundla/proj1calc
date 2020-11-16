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
         * Must check that the identifier references a variable that actually exists
         * (note that identifiers in variable declaration statements are NOT
         * identifierExprs, nor are identifiers in AssignmentStatements)
         */
        if (VarDecl.getVarDeclFromIdent(declaredVars, this.identifier) == null)
            throw new StaticCheckException(this.identifier + " was used in an expression but it has not been declared.");
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        return variables.get(this.identifier);
    }
}
