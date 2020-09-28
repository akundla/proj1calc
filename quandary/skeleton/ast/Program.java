package ast;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class Program extends ASTNode {

    final StatementList statements;

    public Program(List<Statement> statements, Location loc) {
        super(loc);
        this.statements = new StatementList(statements, loc);
    }

    public void println(PrintStream ps) {
        ps.println(this.statements);
    }

    public Object exec(long argument) {
        return this.statements.exec(new HashMap<String, Long>());
    }
}
