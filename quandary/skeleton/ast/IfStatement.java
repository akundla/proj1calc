package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class IfStatement extends Statement {
    
    final Cond condition;
    final Statement statement;

    public IfStatement(Cond cond, Statement statement, Location loc) {
        super(loc);
        this.condition = cond;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "if (" + this.condition.toString() + ") \n\r\t" + this.statement.toString();
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.statement.staticallyCheck(declaredVars, funcRetType);
        // TODO: Finish checking this
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        if (this.condition.eval(variables)) {
            return this.statement.exec(variables);
        }
        else {
            return null;
        }
    }
}
