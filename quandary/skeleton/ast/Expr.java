package ast;

import java.util.HashMap;
import java.util.List;

import ast.BinaryExpr.OPERATOR;
import ast.VarDecl.VAR_TYPE;

public abstract class Expr extends ASTNode {

    Expr(Location loc) {
        super(loc);
    }

    abstract void staticallyCheck(List<VarDecl> declaredVars);

    abstract QuandaryValue eval(HashMap<String, QuandaryValue> variables);

    /**
     * Tries to statically infer the type of the expression. At worst will return Q.
     * @param expr
     * @param declaredVars
     * @return
     */
    public static VAR_TYPE tryInferType(Expr expr, List<VarDecl> declaredVars) {
        // If its an identifier, make sure the the static type of that var is correct
        if (expr instanceof IdentifierExpr) {
            System.out.println(expr);
            return VarDecl.getVarDeclFromIdent(declaredVars, ((IdentifierExpr)expr).identifier).typeCode;
        }
        // If its a cast, make sure that the cast is to the correct type
        else if (expr instanceof CastExpr) {
            return ((CastExpr)expr).varType;
        }
        // If it's a function call expression, make sure that the return type of THAT function is correct
        else if (expr instanceof FunctionCallExpr) {
            FunctionDefinition funcDef = Program.FunctionMap.get( ((FunctionCallExpr)expr).identifier );
            if (funcDef != null)
                return funcDef.functionIdentifier.typeCode;
            else
                return VAR_TYPE.Q;
        }
        // If its a constant expression, its either an int or nil
        else if (expr instanceof ConstExpr) {
            Object constVal = ((ConstExpr)expr).value;
            if (constVal instanceof Long) {
                return VAR_TYPE.INT;
            }
            else {
                return VAR_TYPE.REF;
            }
        }
        // If its a unaryExpr it must be an int
        else if (expr instanceof UnaryExpr) {
            return VAR_TYPE.INT;
        }
        else if (expr instanceof BinaryExpr) {
            OPERATOR op = ((BinaryExpr)expr).op;
            if (op == OPERATOR.DOT)
                return VAR_TYPE.REF;
            else
                return VAR_TYPE.INT;
        }
        else
            return VAR_TYPE.Q;
    }
}
