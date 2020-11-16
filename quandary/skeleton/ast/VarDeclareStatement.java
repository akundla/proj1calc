package ast;

import java.util.HashMap;
import java.util.List;

public class VarDeclareStatement extends Statement {
    
    final VarDecl varDecl;
    final Expr rValue;

    public VarDeclareStatement(VarDecl varDecl, Expr expr, Location loc) {
        super(loc);
        this.varDecl = varDecl;
        this.rValue = expr;
    }

    @Override
    public String toString() {
        return this.varDecl + " = " + this.rValue;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.rValue.staticallyCheck(declaredVars, functionDecl);

        // Start by checking that var is not already declared
        VarDecl shouldBeNullVar = VarDecl.getVarDeclFromIdent(declaredVars, this.varDecl.identifier);
        if (shouldBeNullVar != null)
            throw new StaticCheckException("The variable " + this.varDecl.identifier + " has already been declared.");
        // Now check that what you're assigning it to is of an acceptable type
        else {
            // Add it to the identifiers so it can be seen and not redeclared
            declaredVars.add(this.varDecl);
            AssignmentStatement.staticallyCheckAssignment(this.varDecl, Expr.tryInferType(this.rValue, declaredVars));
        }
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        variables.put(this.varDecl.identifier, (QuandaryValue)this.rValue.eval(variables));
        return null;
    }
}
