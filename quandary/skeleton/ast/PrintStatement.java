package ast;

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
    Object exec() {
        System.out.println(expr.eval());
        return null;
    }
}
