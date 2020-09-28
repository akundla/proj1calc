package ast;

import java.util.HashMap;

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
    Object exec(HashMap<String, Long> variables) {
        if (this.condition.eval()) {
            return this.statement.exec(variables);
        }
        else {
            return null;
        }
    }
}
