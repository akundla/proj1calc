package ast;

public class ReturnStatement extends Statement {

    final Expr expr;
    
    public ReturnStatement(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return this.expr.toString();
    }

    @Override
    Object exec() {
        return this.expr.eval();
    }
}
