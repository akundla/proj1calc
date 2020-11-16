package ast;

import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class WhileLoopStatement extends Statement {
    
    final Cond condition;
    final Statement statement;

    public WhileLoopStatement(Cond cond, Statement statement, Location loc) {
        super(loc);
        this.condition = cond;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "while (" + this.condition.toString() + ") \n\r\t" + this.statement.toString();
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        this.statement.staticallyCheck(declaredVars, funcRetType);
        // TODO: Finish checking
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        while (this.condition.eval(variables)) {
            QuandaryValue statementRetVal = this.statement.exec(variables);
            if (statementRetVal != null) {
                return statementRetVal;
            }
        }
        return null;
    }
}
