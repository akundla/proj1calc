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
