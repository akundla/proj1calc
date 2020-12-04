package ast;

import java.util.HashMap;
import java.util.List;

public class ConcurrentBinaryExpr extends Expr {

    final BinaryExpr binaryExpr;

    public ConcurrentBinaryExpr(BinaryExpr binaryExpr, Location loc) {
        super(loc);
        this.binaryExpr = binaryExpr;
    }

    @Override
    public String toString() {
        return "[" + this.binaryExpr.toString() + "]";
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.binaryExpr.staticallyCheck(declaredVars, functionDecl);
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        return this.binaryExpr.evalConcurrently(variables);
    }
}
