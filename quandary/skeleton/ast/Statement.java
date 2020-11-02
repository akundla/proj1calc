package ast;

import java.util.HashMap;

public abstract class Statement extends ASTNode {
    
    Statement(Location loc) {
        super(loc);
    }

    abstract Object exec(HashMap<String, QuandaryValue> variables);
}
