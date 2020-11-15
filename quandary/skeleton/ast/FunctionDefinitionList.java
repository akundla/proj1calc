package ast;

import java.util.ArrayList;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

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

    /**
     * Requires that staticallyCheck has been run first. Will fail if staticallyCheck() has not been run and the program is statically incorrect.
     * @param argument
     * @return
     */
    public Object exec(long argument) {
        List<QuandaryValue> mainEnv = new ArrayList<QuandaryValue>();
        mainEnv.add(new QuandaryIntValue(argument));
        return FunctionDefinitionList.getFunc(this.functionDefs, FunctionDefinitionList.MAIN).exec(mainEnv);
    }

    /**
     * Checks that:
     * - A main function is present
     * - All function names are unique
     * - Statically checks all function definitions
     */
    public void staticallyCheck() {
        FunctionDefinition main = FunctionDefinitionList.getFunc(this.functionDefs, FunctionDefinitionList.MAIN);
        if (main == null)
            throw new StaticCheckException("Program has no main function.");
        else if (main.formalParameters.size() != 1)
            throw new StaticCheckException("Main function does NOT take just a single argument of type int.");
        else if (main.formalParameters.get(0).typeCode != VAR_TYPE.INT)
            throw new StaticCheckException("Program's main function's first and only parameter is not of type int");

        // Yes, this is an n^2 solution to a problem that could be solved in n time.. I'll get to that later
        for (int i = 0; i < this.functionDefs.size(); i++) {
            FunctionDefinition tempFuncDef = this.functionDefs.remove(i);
            if (getFunc(this.functionDefs, tempFuncDef.functionIdentifier.identifier) != null) {
                throw new StaticCheckException("Two functions have the same name.");
            }
            this.functionDefs.add(i, tempFuncDef);
        }

        // Now check every function definition itself
        for (int i = 0; i < this.functionDefs.size(); i++) {
            this.functionDefs.get(i).staticallyCheck();
        }
    }

    /**
     * Given a list of function definitions and an identifier, returns the first function in the list bearing that identifier.
     * @param funcDefs
     * @param ident
     * @return
     */
    private static FunctionDefinition getFunc(List<FunctionDefinition> funcDefs, String ident) {
        if (funcDefs != null && funcDefs.size() > 0) {
            for (int i = 0; i < funcDefs.size(); i++) {
                if (funcDefs.get(i).functionIdentifier.identifier.equals(ident)) {
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
