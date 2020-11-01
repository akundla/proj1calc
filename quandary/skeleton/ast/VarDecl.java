package ast;

public class VarDecl {
    final boolean isMutable;
    final VAR_TYPE typeCode;
    final String identifier;

    public VarDecl(boolean isMutable, VAR_TYPE typeCode, String ident) {
        this.isMutable = isMutable;
        this.typeCode = typeCode;
        this.identifier = ident;
    }
}
