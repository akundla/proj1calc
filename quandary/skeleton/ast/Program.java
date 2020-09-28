package ast;

import java.io.PrintStream;

public class Program extends ASTNode {

    final FunctionDefinition functionDef;

    public Program(FunctionDefinition funcDef, Location loc) {
        super(loc);
        this.functionDef = funcDef;
    }

    public void println(PrintStream ps) {
        ps.println(this.functionDef);
    }

    public Object exec(long argument) {
        return this.functionDef.exec(argument);
    }
}
