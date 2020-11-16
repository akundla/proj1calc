package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

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
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl funcDecl) {
        this.expr.staticallyCheck(declaredVars, funcDecl);
        VAR_TYPE exprType = Expr.tryInferType(this.expr, declaredVars);
        if (exprType == VAR_TYPE.INT && this.varType == VAR_TYPE.REF)
            throw new StaticCheckException("Cannot cast an int to a ref.");
        else if (exprType == VAR_TYPE.REF && this.varType == VAR_TYPE.INT)
            throw new StaticCheckException("Cannot cast an Ref to an int.");
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        QuandaryValue val = this.expr.eval(variables);
        if (this.varType == VarDecl.VAR_TYPE.INT) {
            if (val instanceof QuandaryIntValue) 
                return (QuandaryIntValue)val;
            else
                throw new DynamicCheckException("Cannot cast a non-int value to an int.");
        }
        else if (this.varType == VarDecl.VAR_TYPE.Q) {
            // This cast cannot fail
            return (QuandaryValue)val;
        }
        else if (this.varType == VarDecl.VAR_TYPE.REF) {
            if (val instanceof QuandaryRefValue)
                return (QuandaryRefValue)val;
            else 
                throw new DynamicCheckException("Cannot cast a non-ref value to a ref.");
        }
        else {
            throw new RuntimeException("Tried to cast to a nonexistent type, or cast failed.");
        }
    }
}
