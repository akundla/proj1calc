package ast;

import java.util.HashMap;
import java.util.List;

import ast.BinaryExpr.OPERATOR;
import ast.VarDecl.VAR_TYPE;

public class ReturnStatement extends Statement {

    final Expr expr;
    
    public ReturnStatement(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return " + this.expr.toString() + ";";
    }

    /**
     * Absolutely massive conditional statement to ensure that the expression returned here is going to be the same type as the return type of the method.
     */
    public void staticallyCheck(List<VarDecl> declaredVars, VAR_TYPE funcRetType) {
        // If its an identifier, make sure the the static type of that var is correct
        if (this.expr instanceof IdentifierExpr) {
            VarDecl retVar = VarDecl.getVarDeclFromIdent(declaredVars, ((IdentifierExpr)this.expr).identifier);
            // VarDecl cannot be null at this point else the method we just called would have thrown an exception
            if (funcRetType == VAR_TYPE.Q) {
                // Anything goes
            }
            else if (funcRetType == VAR_TYPE.INT) {
                // Then this must be an int
                if (retVar.typeCode != VAR_TYPE.INT)
                    throw new StaticCheckException("Function with return type int has return statement to return something that is not an int.");
            }
            else if (funcRetType == VAR_TYPE.REF) {
                // Then this must be a Ref
                if (retVar.typeCode != VAR_TYPE.REF)
                    throw new StaticCheckException("Function with return type Ref has return statement to return something that is not a Ref.");
            }
        }
        // If its a cast, make sure that the cast is to the correct type
        else if (this.expr instanceof CastExpr) {
            VAR_TYPE castType = ((CastExpr)this.expr).varType;
            if (funcRetType == VAR_TYPE.INT) {
                // Then this must be an int
                if (castType != VAR_TYPE.INT)
                    throw new StaticCheckException("Function with return type int has return statement to return something that is cast to something other than an int.");
            }
            else if (funcRetType == VAR_TYPE.REF) {
                // Then this must be a Ref
                if (castType != VAR_TYPE.REF)
                    throw new StaticCheckException("Function with return type Ref has return statement to return something that cast to something other than a Ref.");
            }
        }
        // If it's a function call expression, make sure that the return type of THAT function is correct
        else if (this.expr instanceof FunctionCallExpr) {
            VAR_TYPE retFuncRetType = Program.FunctionMap.get( ((FunctionCallExpr)this.expr).identifier ).functionIdentifier.typeCode;
            if (funcRetType == VAR_TYPE.INT) {
                // Then this must be an int
                if (retFuncRetType != VAR_TYPE.INT)
                    throw new StaticCheckException("Function with return type int has return statement to return result of a function of return type other than an int.");
            }
            else if (funcRetType == VAR_TYPE.REF) {
                // Then this must be a Ref
                if (retFuncRetType != VAR_TYPE.REF)
                    throw new StaticCheckException("Function with return type Ref has return statement to return result of a function with return type other than a Ref.");
            }
        }
        // If its a constant expression, its either an int or nil. Make sure its the right one.
        else if (this.expr instanceof ConstExpr) {
            Object constVal = ((ConstExpr)this.expr).value;
            if (funcRetType == VAR_TYPE.INT) {
                // Then this must be an int
                if (!(constVal instanceof Long))
                    throw new StaticCheckException("Function with return type int has return statement to return const expr that is not an int.");
            }
            else if (funcRetType == VAR_TYPE.REF) {
                // Then this must be a Ref
                if (constVal != null)
                    throw new StaticCheckException("Function with return type Ref has return statement to return const expr that is not nil (note that a val like (1 . 2) is a BinaryExpr and not a constExpr).");
            }
        }
        // If its a unaryExpr it must be an int. Make sure that that's correct.
        else if (this.expr instanceof UnaryExpr) {
            if (funcRetType == VAR_TYPE.REF)
                throw new StaticCheckException("Function with return type Ref has return statement that will return an int (albeit a negative one).");
        }
        // If its a binaryExpr
        else if (this.expr instanceof BinaryExpr) {
            OPERATOR op = ((BinaryExpr)this.expr).op;
            if (funcRetType == VAR_TYPE.INT) {
                // Then this must be an int
                if (op == OPERATOR.DOT)
                    throw new StaticCheckException("Function with return type int has return statement to return binary expr that is not an int.");
            }
            else if (funcRetType == VAR_TYPE.REF) {
                // Then this must be a Ref
                if (op != OPERATOR.DOT)
                    throw new StaticCheckException("Function with return type Ref has return statement to return const expr that is an int and not a ref.");
            }
        }
    }

    /**
     * Here, the one statement that actually returns something! It is of course a quandaryValue
     */
    @Override
    QuandaryValue exec(HashMap<String, QuandaryValue> variables) {
        return this.expr.eval(variables);
    }
}
