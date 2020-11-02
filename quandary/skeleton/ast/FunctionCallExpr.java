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
        String s = identifier + "(";
        for (int i = 0; i < this.arguments.size(); i++) {
            s += this.arguments.get(i) + ", ";
        }
        s += ");";
        return s;
    }

    @Override
    Object eval(HashMap<String, QuandaryValue> variables) {
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        if (func != null) {
            List<QuandaryValue> argVals = new ArrayList<QuandaryValue>();

            for (int i = 0; i < this.arguments.size(); i ++) {
                argVals.add(i, (QuandaryValue)this.arguments.get(i).eval(variables));
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
