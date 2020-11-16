package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class AssignmentStatement extends Statement {
    
    final String ident;
    final Expr rValue;

    public AssignmentStatement(String ident, Expr expr, Location loc) {
        super(loc);
        this.ident = ident;
        this.rValue = expr;
    }

    @Override
    public String toString() {
        return this.ident + " = " + this.rValue;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.rValue.staticallyCheck(declaredVars);

        // Start by checking that var actually exists
        VarDecl var = VarDecl.getVarDeclFromIdent(declaredVars, this.ident);
        if (var == null)
            throw new StaticCheckException("The variable " + ident + " has not been declared.");
        // Now check that what you're assigning it to is of an acceptable type
        else
            AssignmentStatement.staticallyCheckAssignment(var, Expr.tryInferType(this.rValue, declaredVars));
    }

    /**
     * Given a variable declaration and the type of the r value being assigned to it, performs static checking.
     * Needed for both declare and assign statements.
     * @param var
     * @param rValType
     */
    public static void staticallyCheckAssignment(VarDecl var, VAR_TYPE rValType) {
        if (var.typeCode == VAR_TYPE.Q) {
            // You can assign anything to a Q
        }
        else if (var.typeCode == VAR_TYPE.INT && rValType != VAR_TYPE.INT)
            throw new StaticCheckException(var.identifier + " is an int but was assigned a value that was not or not explicity an int.");
        else if (var.typeCode == VAR_TYPE.REF && rValType != VAR_TYPE.REF)
            throw new StaticCheckException(var.identifier + " is a Ref but was assigned a value that was not or not explicity a Ref.");
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.ident, this.rValue.eval(variables));
        return null;
    }
}
