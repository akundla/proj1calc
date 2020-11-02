package ast;

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
        for (VarDecl param : this.formalParameters) {
            s += param.toString() + ", ";
        }
        s += ") " + this.statements;
        return s;
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
