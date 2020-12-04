package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ast.VarDecl.VAR_TYPE;

public class FunctionCallExpr extends Expr {

    public static final String LEFT_IDENT = "left";
    public static final String RIGHT_IDENT = "right";
    public static final String RANDOM_INT_IDENT = "randomInt";
    public static final String ISATOM_IDENT = "isAtom";
    public static final String ISNIL_IDENT = "isNil";
    public static final String SETLEFT_IDENT = "setLeft";
    public static final String SETRIGHT_IDENT = "setRight";

    final String identifier;
    final List<Expr> arguments;

    public FunctionCallExpr(String identifier, List<Expr> arguments, Location loc) {
        super(loc);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String s = identifier + "(";
        for (int i = 0; i < this.arguments.size(); i++) {
            s += this.arguments.get(i);
            if (i < this.arguments.size() - 1)
                s += ", ";
        }
        s += ")";
        return s;
    }

    @Override
    public void staticallyCheck(List<VarDecl> declaredVars, VarDecl functionDecl) {
        // Find the function in the map
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        // If it's there great, but if not, check if it's a predef
        if (func == null && !FunctionCallExpr.isPredefFunc(this.identifier))
            // If it's not a predef either, then it doesn't exist. Throw static error.
            throw new StaticCheckException(this.identifier + " is not a defined or predefined function.");
        // If function decl is mutable then we can call whatever we want but if not we can only call immtable functions
        else if (!functionDecl.isMutable) {
            if (func == null) {
                if (FunctionCallExpr.isPredefFunc(this.identifier)) {
                    if (FunctionCallExpr.SETLEFT_IDENT.equals(this.identifier) || FunctionCallExpr.SETRIGHT_IDENT.equals(this.identifier)) {
                        throw new StaticCheckException("Cannot call a mutable function from inside an immutable function.");
                    }
                }
            }
            else if (func.functionIdentifier.isMutable) {
                throw new StaticCheckException("Cannot call a mutable function from inside an immutable function.");
            }
        }

        // Check every parameter
        if (func != null) {
            if (func.formalParameters.size() != this.arguments.size())
                throw new StaticCheckException("Function Call expression does not have the same number of arguments as the function being called has formal parameters.");
            else {
                for (int i = 0; i < this.arguments.size(); i++) {

                    /*
                    System.out.println("Formal parameter type: " + func.formalParameters.get(i).typeCode
                        + ", Argument: " + this.arguments.get(i)
                        + ", Argument type: " + Expr.tryInferType(this.arguments.get(i), declaredVars));
                    */
                    
                    this.arguments.get(i).staticallyCheck(declaredVars, functionDecl);

                    if (((func.formalParameters.get(i).typeCode == VAR_TYPE.INT) && (Expr.tryInferType(this.arguments.get(i), declaredVars) != VAR_TYPE.INT))
                        || ((func.formalParameters.get(i).typeCode == VAR_TYPE.REF) && (Expr.tryInferType(this.arguments.get(i), declaredVars) != VAR_TYPE.REF)))
                        throw new StaticCheckException("Type of argument to function did not match type of formal parameter.");
                }
            }
        }
        else if (FunctionCallExpr.SETLEFT_IDENT.equals(this.identifier) || FunctionCallExpr.SETRIGHT_IDENT.equals(this.identifier)) {
            if (this.arguments.size() != 2)
                throw new StaticCheckException("setLeft() or setRight() was called with the wrong number of parameters.");
            else {
                if (Expr.tryInferType(this.arguments.get(0), declaredVars) != VAR_TYPE.REF)
                    throw new StaticCheckException("First argument to setLeft() or setRight() was not a ref.");
            }
        }
        // This must be one of the other predefined functions, all of which take one parameter
        else {
            if (this.arguments.size() != 1)
                throw new StaticCheckException("Predefined function called with the wrong number of parameters.");
            else {
                if (FunctionCallExpr.ISATOM_IDENT.equals(this.identifier) || FunctionCallExpr.ISNIL_IDENT.equals(this.identifier)) {
                    // Since the first and only param to these 2 is a Q, anything will do
                }
                else if (FunctionCallExpr.RANDOM_INT_IDENT.equals(this.identifier)) {
                    if (Expr.tryInferType(this.arguments.get(0), declaredVars) != VAR_TYPE.INT)
                        throw new StaticCheckException("First argument to randomInt() was not an int.");
                }
                else if (FunctionCallExpr.LEFT_IDENT.equals(this.identifier) || FunctionCallExpr.RIGHT_IDENT.equals(this.identifier)){
                    // Must be left() or right()
                    if (Expr.tryInferType(this.arguments.get(0), declaredVars) != VAR_TYPE.REF)
                        throw new StaticCheckException("First argument to left() or right() was not a ref.");
                }

            }
        }

    }

    /**
     * Returns if the identifier given matches one of the predefined functions
     * 
     * @param identifier
     * @return
     */
    public static boolean isPredefFunc(String identifier) {
        if (identifier.equals(FunctionCallExpr.LEFT_IDENT) || identifier.equals(FunctionCallExpr.RIGHT_IDENT)
                || identifier.equals(FunctionCallExpr.RANDOM_INT_IDENT)
                || identifier.equals(FunctionCallExpr.ISATOM_IDENT) || identifier.equals(FunctionCallExpr.ISNIL_IDENT)
                || identifier.equals(FunctionCallExpr.SETLEFT_IDENT)
                || identifier.equals(FunctionCallExpr.SETRIGHT_IDENT))
            return true;
        else
            return false;
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        if (func != null) {
            List<QuandaryValue> argVals = new ArrayList<QuandaryValue>();

            for (int i = 0; i < this.arguments.size(); i++) {
                argVals.add(i, (QuandaryValue) this.arguments.get(i).eval(variables));
            }

            return func.exec(argVals);
        } else if (this.identifier.equals(RANDOM_INT_IDENT)) {
            return FunctionCallExpr.randomInt(
                    // This had better be a QuandaryIntValue (else it is a statically incorrect
                    // program)
                    (QuandaryIntValue) this.arguments.get(0).eval(variables));
        } else if (this.identifier.equals(LEFT_IDENT)) {
            return FunctionCallExpr
                    .left(FunctionCallExpr.nilChecker((QuandaryRefValue) this.arguments.get(0).eval(variables)));
        } else if (this.identifier.equals(RIGHT_IDENT)) {
            return FunctionCallExpr
                    .right(FunctionCallExpr.nilChecker((QuandaryRefValue) this.arguments.get(0).eval(variables)));
        } else if (this.identifier.equals(ISATOM_IDENT)) {
            return FunctionCallExpr.isAtom(this.arguments.get(0).eval(variables));
        } else if (this.identifier.equals(ISNIL_IDENT)) {
            return FunctionCallExpr.isNil(this.arguments.get(0).eval(variables));
        } else if (this.identifier.equals(SETLEFT_IDENT)) {
            return FunctionCallExpr.setLeft(
                    FunctionCallExpr.nilChecker((QuandaryRefValue) this.arguments.get(0).eval(variables)),
                    this.arguments.get(1).eval(variables));
        } else if (this.identifier.equals(SETRIGHT_IDENT)) {
            return FunctionCallExpr.setRight(
                    FunctionCallExpr.nilChecker((QuandaryRefValue) this.arguments.get(0).eval(variables)),
                    this.arguments.get(1).eval(variables));
        }
        throw new RuntimeException("FunctionCallExpr was given an unrecognizable identifier.");
    }

    // int randomInt(int n) - Returns a random int in [0; n)
    private static QuandaryIntValue randomInt(QuandaryIntValue n) {
        long randomVal = (long) (n.value * Math.random());
        return new QuandaryIntValue(randomVal);
    }

    // Q left(Ref r) - Returns the left field of the object referenced by r
    private static QuandaryValue left(QuandaryRefValue r) {
        return r.referencedQObject.left;
    }

    // Q right(Ref r) - Returns the right field of the object referenced by r
    private static QuandaryValue right(QuandaryRefValue r) {
        return r.referencedQObject.right;
    }

    // int isAtom(Q x) – Returns 1 if x is nil or an int, and 0 otherwise (it is a
    // Ref)
    private static QuandaryIntValue isAtom(QuandaryValue x) {
        if (x instanceof QuandaryIntValue) {
            return new QuandaryIntValue(1);
        } else if (x instanceof QuandaryRefValue) {
            QuandaryRefValue refX = (QuandaryRefValue) x;
            // Is x nil?
            if (refX.referencedQObject == null) {
                return new QuandaryIntValue(1);
            } else {
                return new QuandaryIntValue(0);
            }
        } else {
            throw new RuntimeException(
                    "QuandaryValue x passed to isAtom was neither instance of QuandaryIntValue nor QuandaryRefValue.");
        }
    }

    // int isNil(Q x) – Returns 1 if x is nil; returns 0 otherwise (it is an int or
    // Ref)
    private static QuandaryIntValue isNil(QuandaryValue x) {
        if (x instanceof QuandaryIntValue) {
            return new QuandaryIntValue(0);
        } else if (x instanceof QuandaryRefValue) {
            QuandaryRefValue refX = (QuandaryRefValue) x;
            // Is x nil?
            if (refX.referencedQObject == null) {
                return new QuandaryIntValue(1);
            } else {
                return new QuandaryIntValue(0);
            }
        } else {
            throw new RuntimeException(
                    "QuandaryValue x passed to isAtom was neither instance of QuandaryIntValue nor QuandaryRefValue.");
        }
    }

    // mutable int setLeft(Ref r, Q value) – Sets the left field of the object
    // referenced by r to value, and returns 1
    private static QuandaryIntValue setLeft(QuandaryRefValue r, QuandaryValue value) {
        FunctionCallExpr.checkTypes(r.referencedQObject.left, value);
        r.referencedQObject.left = value;
        return new QuandaryIntValue(1);
    }

    // mutable int setRight(Ref r, Q value) – Sets the right field of the object
    // referenced by r to value, and returns 1
    private static QuandaryIntValue setRight(QuandaryRefValue r, QuandaryValue value) {
        FunctionCallExpr.checkTypes(r.referencedQObject.right, value);
        r.referencedQObject.right = value;
        return new QuandaryIntValue(1);
    }

    // Helper function to detect nil dereferences
    private static QuandaryRefValue nilChecker(QuandaryRefValue r) {
        // Should be impossible.
        if (r == null)
            throw new NilDereferenceException(
                    "BIG PROBLEM: r itself is nil in left(), right(), setLeft(), or setRight().");
        // If you call left() on a nil ref the program is dynamically incorrect
        if (r.referencedQObject == null)
            throw new NilDereferenceException(
                    "Nil dereference error on built-in function left(), right(), setLeft(), or setRight().");
        return r;
    }

    // Enforces rule precluding heap mutation.
    private static void checkTypes(QuandaryValue field, QuandaryValue newVal) {
        if (field instanceof QuandaryIntValue) {
            if (!(newVal instanceof QuandaryIntValue))
                throw new DynamicCheckException(
                        "Cannot assign an int field in the heap to anything other than an int.");
        } else if (field instanceof QuandaryRefValue) {
            if (!(newVal instanceof QuandaryRefValue))
                throw new DynamicCheckException("Cannot assign a Ref field in the heap to anything other than a Ref.");
        }
    }
}
