package ast;

public class StaticCheckException extends RuntimeException {
    private static final long serialVersionUID = -5235196466601142499L;

    public StaticCheckException(String s) {
        super(s);
    }
}
