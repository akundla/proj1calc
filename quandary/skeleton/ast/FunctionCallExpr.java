package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionCallExpr extends Expr {

    private static final String RANDOM_INT_IDENT = "randomInt";

    final String identifier;
    final List<Expr> arguments;

    public FunctionCallExpr(String identifier, List<Expr> arguments, Location loc) {
        super(loc);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return identifier + "(" + "" + ");";
    }

    @Override
    Object eval(HashMap<String, Long> variables) {
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        if (func != null) {
            List<Long> argVals = new ArrayList<Long>();

            for (int i = 0; i < this.arguments.size(); i ++) {
                argVals.add(i, (long)this.arguments.get(i).eval(variables));
            }
            
            return func.exec(argVals);
        }
        else if (this.identifier.equals(RANDOM_INT_IDENT)) {
            long n = (long)this.arguments.get(0).eval(variables);
            return (long)(n * Math.random());
        }
        else {
            return null;
        }
    }
}