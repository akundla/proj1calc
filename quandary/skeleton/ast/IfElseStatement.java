package ast;

import java.util.HashMap;

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
            + "\n\r" + "else"
            + "\n\r\t" + this.elseStatement.toString();
    }

    @Override
    Object exec(HashMap<String, Long> variables) {
        if (this.condition.eval(variables)) {
            return this.statement1.exec(variables);
        }
        else {
            return this.elseStatement.exec(variables);
        }
    }
}
