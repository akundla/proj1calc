package ast;

import java.util.HashMap;
import java.util.List;

public class FunctionDefinition extends ASTNode {

    final String functionName;
    final List<String> formalParameters;
    final StatementList statements;

    public FunctionDefinition(String funcName, List<String> formalParams, List<Statement> statements, Location loc) {
        super(loc);
        this.functionName = funcName;
        this.formalParameters = formalParams;
        this.statements = new StatementList(statements, loc);
    }

    public String toString() {
        String s = this.functionName + " (";
        for (String param : this.formalParameters) {
            s += "int " + param + ", ";
        }
        s += ") " + this.statements;
        return s;
    }

    public Object exec(HashMap<String, Long> params) {
        return this.statements.exec(params);
    }
}
