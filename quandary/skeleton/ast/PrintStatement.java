package ast;

import java.util.HashMap;
import java.util.List;

public class PrintStatement extends Statement {

    final Expr expr;
    
    public PrintStatement(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "print(" + this.expr.toString() + ");";
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.expr.staticallyCheck(declaredVars, functionDecl);
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        System.out.println(expr.eval(variables));
        return null;
    }
}
