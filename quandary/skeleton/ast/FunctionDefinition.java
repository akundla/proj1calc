package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionDefinition extends ASTNode {

    final VarDecl functionIdentifier;
    final List<VarDecl> formalParameters;
    final StatementList statements;

    public FunctionDefinition(VarDecl funcIdent, List<VarDecl> formalParams, List<Statement> statements, Location loc) {
        super(loc);
        this.functionIdentifier = funcIdent;
        this.formalParameters = formalParams;
        this.statements = new StatementList(statements, loc);
    }

    public String toString() {
        String s = this.functionIdentifier.identifier + " (";
        for (int i = 0; i < this.formalParameters.size(); i++) {
            s += this.formalParameters.get(i);
            if (i < this.formalParameters.size() - 1) 
                s += ", ";
        }
        s += ") " + this.statements;
        return s;
    }

    /**
     * Checks that the function name is not one of the names of a predefined function
     */
    public void staticallyCheck() {
        // Check everything there is to check about the funciton identifier
        if (functionIdentifier.identifier.equals(FunctionCallExpr.LEFT_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.RIGHT_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.RANDOM_INT_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.ISATOM_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.ISNIL_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.SETLEFT_IDENT)
            || functionIdentifier.identifier.equals(FunctionCallExpr.SETRIGHT_IDENT) )
            throw new StaticCheckException("Function name may not be the same as one of the predefined functions.");
        
        // Perform normal static checking
        this.statements.staticallyCheck(
            // Copy the formal parameter list because it will not be restored.
            new ArrayList<VarDecl>(this.formalParameters),
            this.functionIdentifier.typeCode);

        // Check that the function ends on a return statement
        StatementList.checkRetForMethodBody(this.statements);
    }

    // Functions must always return a value (hence why all have a return type of int, q, or ref), so this will return a QuandaryValue
    public QuandaryValue exec(List<QuandaryValue> params) {
        HashMap<String, QuandaryValue> localEnv = new HashMap<String, QuandaryValue>();

        for (int i = 0; i < this.formalParameters.size(); i++) {
            localEnv.put(this.formalParameters.get(i).identifier, params.get(i));
        }

        // Not all statementLists return a value, but all function bodies do, so this cast is safe for a statically correct program
        return (QuandaryValue)this.statements.exec(localEnv);
    }
}
