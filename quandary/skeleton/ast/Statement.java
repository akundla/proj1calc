package ast;

import java.util.HashMap;

public abstract class Statement extends ASTNode {
    
    Statement(Location loc) {
        super(loc);
    }

    /**
     * Only return statements return a value. However, statementLists may contain return statements, so they may return a value as well. 
     * Since both inherit from here, the return value for statement.exec() is QuandaryValue
     * When this return value is null, there was no return value.
     * (The return type used to be Object, which was used in the same manner.)
     */
    abstract QuandaryValue exec(HashMap<String, QuandaryValue> variables);
}
