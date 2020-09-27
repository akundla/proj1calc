package ast;

import java.io.PrintStream;

public class Program extends ASTNode {

    final Statement statement;

    public Program(Statement statement, Location loc) {
        super(loc);
        this.statement = statement;
    }

    public void println(PrintStream ps) {
        ps.println(this.statement);
    }

    public Object exec(long argument) {
        return this.statement.exec();
    }
}
