package ast;

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
        String s = null;
        for (FunctionDefinition functionDef : this.functionDefs) {
            s += functionDef + "\n\r";
        }
        return s;
    }

    public Object exec() {
        FunctionDefinition main = FunctionDefinitionList.getMain(this.functionDefs);
        if (main != null) {
            return main.exec();
        }
        else {
            return null;
        }
    }

    private static FunctionDefinition getMain(List<FunctionDefinition> funcDefs) {
        if (funcDefs != null && funcDefs.size() > 0) {

            // TODO: Delete once we have identifiers
            return funcDefs.get(0);

            /*
            for (int i = 0; i < funcDefs.size(); i++) {
                if (funcDefs.get(i).name.equals(MAIN)) {
                    return funcDefs.get(i);
                }
            }
            return null;
            */
        }
        else {
            return null;
        }
    }
}
