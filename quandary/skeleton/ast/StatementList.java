package ast;

import java.util.List;

public class StatementList extends Statement {

    final List<Statement> statements;

    public StatementList(List<Statement> statements, Location loc) {
        super(loc);
        this.statements = statements;
    }

    @Override
    public String toString() {
        String s = null;
        for (Statement statement : this.statements) {
            s += statement + "\n\r";
        }
        return s;
    }

    public Object exec() {
        for (int i = 0; i < this.statements.size(); i++) {
            Object tempVal = this.statements.get(i).exec();
            if (tempVal != null) {
                return tempVal;
            }
        }
        return null;
    }
}
