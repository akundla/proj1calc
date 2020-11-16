package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public abstract class Statement extends ASTNode {
    
    public Statement(Location loc) {
        super(loc);
    }

    abstract void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType);

    // Each statement needs a staticallyCheck method too, but those take all different parameters

    /**
     * Only return statements return a value. However, statementLists may contain return statements, so they may return a value as well. 
     * Since both inherit from here, the return value for statement.exec() is QuandaryValue
     * When this return value is null, there was no return value.
     * (The return type used to be Object, which was used in the same manner.)
     */
    abstract QuandaryValue exec(HashMap<String, QuandaryValue> variables);
}
