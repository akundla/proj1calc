package ast;

import java.util.HashMap;

public class CastExpr extends Expr {

    final VarDecl.VAR_TYPE varType;
    final Expr expr;

    public CastExpr(VarDecl.VAR_TYPE varType, Expr expr, Location loc) {
        super(loc);
        this.varType = varType;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "(" + this.varType.name() + ")" + this.expr;
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        QuandaryValue val = this.expr.eval(variables);
        if (this.varType == VarDecl.VAR_TYPE.INT) {
            return (QuandaryIntValue)val;
        }
        else if (this.varType == VarDecl.VAR_TYPE.Q) {
            return (QuandaryValue)val;
        }
        else if (this.varType == VarDecl.VAR_TYPE.REF) {
            return (QuandaryRefValue)val;
        }
        else {
            throw new RuntimeException("Tried to cast to a nonexistent type, or cast failed.");
        }
    }
}
