package ast;

public class NilDereferenceException extends RuntimeException {
    private static final long serialVersionUID = 2314043247643440188L;

    public NilDereferenceException(String s) {
        super(s);
    }
}