package ast;

import java.io.PrintStream;

public class Program extends ASTNode {

    final Cond cond;

    public Program(Cond cond, Location loc) {
        super(loc);
        this.cond = cond;
    }

    public void println(PrintStream ps) {
        ps.println(cond);
    }

    public Object exec(long argument) {
        return cond.eval();
    }
}
