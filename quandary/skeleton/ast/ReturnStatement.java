package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class ReturnStatement extends Statement {

    final Expr expr;
    
    public ReturnStatement(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return " + this.expr.toString() + ";";
    }

    /**
     * Ensures that the expression returned here is going to be the same type as the return type of the method.
     */
    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {

        this.expr.staticallyCheck(declaredVars, functionDecl);

        VAR_TYPE exprType = Expr.tryInferType(this.expr, declaredVars);

        if (functionDecl.typeCode == VAR_TYPE.Q) {
            // Anything goes
        }
        else if (functionDecl.typeCode == VAR_TYPE.INT && exprType != VAR_TYPE.INT)
            throw new StaticCheckException("Function with return type int has return statement to return something that is not an int.");
        else if (functionDecl.typeCode == VAR_TYPE.REF && exprType != VAR_TYPE.REF)
            throw new StaticCheckException("Function with return type Ref has return statement to return something that is not a Ref.");
    }

    /**
     * Here, the one statement that actually returns something! It is of course a quandaryValue
     */
    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        return this.expr.eval(variables);
    }
}
