package ast;

import java.util.HashMap;
import java.util.List;

public class FunctionDefinition extends ASTNode {

    final String functionName;
    final StatementList statements;
    final HashMap<String, Long> varEnv;

    public FunctionDefinition(String funcName, List<Statement> statements, Location loc) {
        super(loc);
        this.functionName = funcName;
        this.statements = new StatementList(statements, loc);
        this.varEnv = new HashMap<String, Long>();
    }

    public String toString() {
        return "(" + "" + ")" + "{\n\r" + this.statements + "\n\r}";
    }

    public Object exec() {
        return this.statements.exec(this.varEnv);
    }
}
