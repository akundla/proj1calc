package ast;

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
    Object exec() {
        if (this.condition.eval()) {
            return this.statement.exec();
        }
        else {
            return null;
        }
    }
}
