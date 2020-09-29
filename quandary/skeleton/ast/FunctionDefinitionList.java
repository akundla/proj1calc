package ast;

import java.util.HashMap;
import java.util.List;

public class FunctionDefinitionList extends ASTNode {

    private static final String MAIN = "main";

    final List<FunctionDefinition> functionDefs;

    public FunctionDefinitionList(List<FunctionDefinition> funcDefs, Location loc) {
        super(loc);
        this.functionDefs = funcDefs;
    }

    @Override
    public String toString() {
        String s = "";
        for (FunctionDefinition functionDef : this.functionDefs) {
            s += functionDef + "\n\r";
        }
        return s;
    }

    public Object exec(long argument) {
        FunctionDefinition main = FunctionDefinitionList.getMain(this.functionDefs);
        if (main != null) {
            HashMap<String, Long> mainEnv = new HashMap<String, Long>();
            mainEnv.put("arg", argument);
            return main.exec(mainEnv);
        }
        else {
            return null;
        }
    }

    private static FunctionDefinition getMain(List<FunctionDefinition> funcDefs) {
        if (funcDefs != null && funcDefs.size() > 0) {
            for (int i = 0; i < funcDefs.size(); i++) {
                if (funcDefs.get(i).functionName.equals(MAIN)) {
                    return funcDefs.get(i);
                }
            }
            return null;
        }
        else {
            return null;
        }
    }
}
