package ast;

import java.util.HashMap;
import java.util.List;

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
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        this.statement.staticallyCheck(declaredVars, functionDecl);
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
