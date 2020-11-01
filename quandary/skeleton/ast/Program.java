package ast;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class Program extends ASTNode {

    public static final HashMap<String, FunctionDefinition> FunctionMap = new HashMap<String, FunctionDefinition>();

    final FunctionDefinitionList functionDefs;

    public Program(List<FunctionDefinition> funcDefs, Location loc) {
        super(loc);
        this.functionDefs = new FunctionDefinitionList(funcDefs, loc);

        for (int i = 0; i < funcDefs.size(); i++) {
            Program.FunctionMap.put(funcDefs.get(i).functionIdentifier.identifier, funcDefs.get(i));
        }
    }

    public void println(PrintStream ps) {
        ps.println(this.functionDefs);
    }

    public Object exec(long argument) {
        return this.functionDefs.exec(argument);
    }
}
