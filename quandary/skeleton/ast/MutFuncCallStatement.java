package ast;

import java.util.HashMap;
import java.util.List;

public class MutFuncCallStatement extends Statement {
    
    final FunctionCallExpr funcCallExpr;

    public MutFuncCallStatement(FunctionCallExpr funcCallExpr, Location loc) {
        super(loc);
        this.funcCallExpr = funcCallExpr;
    }

    @Override
    public String toString() {
        return this.funcCallExpr.toString() + ";";
    }

    /**
     * For this statement to be statically correct:
     * 1. The function it's in has to be mutable, BECAUSE
     * 2. The function being called also has to be mutable (otherwise this statement would be pointless).
     */
    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        if (!functionDecl.isMutable)
            throw new StaticCheckException("Cannot even attempt to call any mutable functions from a non-mutable function.");

        this.funcCallExpr.staticallyCheck(declaredVars, functionDecl);

        // If we made it this far then the function does actually exist
        FunctionDefinition func = Program.FunctionMap.get(this.funcCallExpr.identifier);
        if (func != null) {
            if (!func.functionIdentifier.isMutable)
                throw new StaticCheckException("Cannot call non-mutable function in a Function Call Statement.");
        }
        else if (FunctionCallExpr.isPredefFunc(this.funcCallExpr.identifier) 
            && !(
                FunctionCallExpr.SETLEFT_IDENT.equals(this.funcCallExpr.identifier)
                || FunctionCallExpr.SETRIGHT_IDENT.equals(this.funcCallExpr.identifier)
                || FunctionCallExpr.ACQ_IDENT.equals(this.funcCallExpr.identifier)
                || FunctionCallExpr.REL_IDENT.equals(this.funcCallExpr.identifier)
                )
            ) {
            throw new StaticCheckException("Cannot call non-mutable function in a Function Call Statement (even a predefined one).");
        }
    }

    /**
     * If this was called then the return value is meant to be ignored, so ret null.
     */
    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        funcCallExpr.eval(variables);
        return null;
    }
}
