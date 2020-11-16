package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class IfElseStatement extends Statement {
    
    final Cond condition;
    final Statement statement1;
    final Statement elseStatement;

    public IfElseStatement(Cond cond, Statement statement1, Statement elseStatement, Location loc) {
        super(loc);
        this.condition = cond;
        this.statement1 = statement1;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "if (" + this.condition.toString() + ") \n\r\t" + this.statement1.toString()
            + "\n\r\t" + "else"
            + "\n\r\t" + this.elseStatement.toString();
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.statement1.staticallyCheck(declaredVars, funcRetType);
        this.elseStatement.staticallyCheck(declaredVars, funcRetType);
        // TODO: Finish checking this later
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        if (this.condition.eval(variables)) {
            return this.statement1.exec(variables);
        }
        else {
            return this.elseStatement.exec(variables);
        }
    }
}
