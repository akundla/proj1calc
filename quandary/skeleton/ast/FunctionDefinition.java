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

    public Object exec(List<Long> params) {
        HashMap<String, Long> localEnv = new HashMap<String, Long>();

        for (int i = 0; i < this.formalParameters.size(); i++) {
            localEnv.put(this.formalParameters.get(i).identifier, params.get(i));
        }

        return this.statements.exec(localEnv);
    }
}
