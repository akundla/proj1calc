package ast;

public abstract class Cond extends ASTNode {
    
    Cond(Location loc) {
        super(loc);
    }

    abstract boolean eval();

    /**
     * 0 denotes boolean false, and anything else denotes boolean true.
     *  Thus we use val != 0, because if the value is not equal to 0, it denotes boolean true.
     */
    static boolean isTruthy(long value) {
        return (value != 0);
    }
}
