package ast;

import java.util.HashMap;

public class UnaryCond extends Cond {

    public static final int BOOL_NOT = 1;

    final int operator;
    final Cond cond;

    public UnaryCond(int operator, Cond cond, Location loc) {
        super(loc);
        this.operator = operator;
        this.cond = cond;
    }

    @Override
    public String toString() {
        return "(" + simpleString() + ")";
    }

    public String simpleString() {
        String s = null;
        switch (operator) {
            case BOOL_NOT:  s = "!"; break;
        }
        return s + " " + cond;
    }

    @Override
    boolean eval(HashMap<String, QuandaryValue> variables) {
        return doOperation(operator, cond.eval(variables));
    }

    static boolean doOperation(int operator, boolean value) {
        switch (operator) {
            case BOOL_NOT:  return !value;
        }
        throw new RuntimeException("Unexpected in UnaryCond.doOperation");
    }

    
}
