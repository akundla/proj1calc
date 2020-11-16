package ast;

import java.util.HashMap;
import java.util.List;

public class StatementList extends Statement {

    final List<Statement> statements;

    public StatementList(List<Statement> statements, Location loc) {
        super(loc);
        this.statements = statements;
    }

    @Override
    public String toString() {
        String s = "{\n\r";
        for (Statement statement : this.statements) {
            s += "\t" + statement + "\n\r";
        }
        s += "\t}\n\r";
        return s;
    }

    /**
     * Simply checks every statement
     */
    @Override
    public void staticallyCheck() {
        for (int i = 0; i < this.statements.size(); i++) {
            this.statements.get(i).staticallyCheck();
        }
    }

    /**
     * Must be called from Function definition statically check
     * AND requires that staticallyCheck already be called
     */
    public static void checkRetForMethodBody(StatementList sl) {
        if (sl.statements == null) 
            throw new StaticCheckException("Method body must not be null.");
        if (sl.statements.size() < 1)
            throw new StaticCheckException("Method body must have at least one statement.");
        if (!(sl.statements.get(sl.statements.size() - 1) instanceof ReturnStatement))
            throw new StaticCheckException("Last statement of function body is not a return statement.");
    }

    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        for (int i = 0; i < this.statements.size(); i++) {
            QuandaryValue tempVal = this.statements.get(i).exec(variables);
            if (tempVal != null) {
                return tempVal;
            }
        }
        return null;
    }
}
