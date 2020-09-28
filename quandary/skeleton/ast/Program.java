package ast;

import java.io.PrintStream;
import java.util.List;

public class Program extends ASTNode {

    final FunctionDefinitionList functionDefs;

    public Program(List<FunctionDefinition> funcDefs, Location loc) {
        super(loc);
        this.functionDefs = new FunctionDefinitionList(funcDefs, loc);
    }

    public void println(PrintStream ps) {
        ps.println(this.functionDefs);
    }

    public Object exec(long argument) {
        return this.functionDefs.exec();
    }
}
