package ast;

import java.util.HashMap;

public class QThread extends Thread {

    private final HashMap<String, QuandaryValue> variables;
    private final Expr e;
    public QuandaryValue val;

    public QThread(Expr e, HashMap<String, QuandaryValue> variables) {
        this.e = e;
        this.variables = variables;
    }

    @Override
    public void run() {
		this.e.eval(variables);
    }
}
