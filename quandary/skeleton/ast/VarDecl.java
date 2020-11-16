package ast;

import java.util.List;

public class VarDecl {
    public static enum VAR_TYPE {
        INT,
        REF,
        Q
    }

    final boolean isMutable;
    final VAR_TYPE typeCode;
    final String identifier;

    public VarDecl(boolean isMutable, VAR_TYPE typeCode, String ident) {
        this.isMutable = isMutable;
        this.typeCode = typeCode;
        this.identifier = ident;
    }

    @Override
    public String toString() {
        String s = "";
        if (this.isMutable) {
            s = "mutable" + " ";
        }

        if (this.typeCode == VAR_TYPE.INT) {
            s += "int";
        }
        else if (this.typeCode == VAR_TYPE.Q) {
            s += "Q";
        }
        else if (this.typeCode == VAR_TYPE.REF) {
            s += "Ref";
        }
        return s + " " + this.identifier;
    }

    public static VarDecl getVarDeclFromIdent(List<VarDecl> declaredVars, String ident) {
        for (int i = 0; i < declaredVars.size(); i++) {
            VarDecl temp = declaredVars.get(i);
            if (temp.identifier.equals(ident)) {
                return temp;
            }
        }
        throw new StaticCheckException("The variable " + ident + " has not been declared.");
    }
}
