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
            Program.FunctionMap.put(funcDefs.get(i).functionName, funcDefs.get(i));
        }
    }

    public void println(PrintStream ps) {
        ps.println(this.functionDefs);
    }

    public Object exec(long argument) {
        this.println(System.out);
        return 55;
        //return this.functionDefs.exec(argument);
    }
}
