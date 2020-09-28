package ast;

import java.util.HashMap;

public class FunctionCallExpr extends Expr {

    final String identifier;

    public FunctionCallExpr(String identifier, Location loc) {
        super(loc);
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier + "(" + "" + ");";
    }

    @Override
    Object eval(HashMap<String, Long> variables) {
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        if (func != null) {
            return func.exec();
        }
        else {
            return null;
        }
    }
}
