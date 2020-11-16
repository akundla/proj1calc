package ast;

import java.util.HashMap;
import java.util.List;

public abstract class Cond extends ASTNode {
    
    Cond(Location loc) {
        super(loc);
    }

    abstract void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl);

    abstract boolean eval(HashMap<String, QuandaryValue> variables);

    /**
     * 0 denotes boolean false, and anything else denotes boolean true.
     *  Thus we use val != 0, because if the value is not equal to 0, it denotes boolean true.
     */
    static boolean isTruthy(long value) {
        return (value != 0);
    }
}
