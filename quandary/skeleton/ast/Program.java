package ast;

import java.io.PrintStream;
import java.util.List;

public class Program extends ASTNode {

    final List<Statement> statements;

    public Program(List<Statement> statements, Location loc) {
        super(loc);
        this.statements = statements;
    }

    public void println(PrintStream ps) {
        for (Statement statement : this.statements) {
            ps.println(statement);
        }
    }

    public Object exec(long argument) {
        for (int i = 0; i < this.statements.size(); i++) {
            Object tempVal = this.statements.get(i).exec();
            if (tempVal != null) {
                return tempVal;
            }
        }
        return null;
    }
}
