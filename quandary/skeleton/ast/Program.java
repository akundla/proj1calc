package ast;

import java.io.PrintStream;

public class Program extends ASTNode {

    final StatementList statements;

    public Program(StatementList statements, Location loc) {
        super(loc);
        this.statements = statements;
    }

    public void println(PrintStream ps) {
        ps.println(this.statements);
    }

    public Object exec(long argument) {
        return this.statements.exec();
    }
}
