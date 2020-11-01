package ast;

import java.util.HashMap;

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
    Object exec(HashMap<String, Long> variables) {
        while (this.condition.eval(variables)) {
            Object statementRetVal = this.statement.exec(variables);
            if (statementRetVal != null) {
                return statementRetVal;
            }
        }
        return null;
    }
}
