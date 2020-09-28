package ast;

import java.util.HashMap;
import java.util.List;

public class FunctionDefinition extends ASTNode {

    final StatementList statements;
    final HashMap<String, Long> varEnv;

    public FunctionDefinition(List<Statement> statements, Location loc) {
        super(loc);
        this.statements = new StatementList(statements, loc);
        this.varEnv = new HashMap<String, Long>();
    }

    public String toString() {
        return "(" + "" + ")" + "{\n\r" + this.statements + "\n\r}";
    }

    public Object exec(long argument) {
        return this.statements.exec(this.varEnv);
    }
}
