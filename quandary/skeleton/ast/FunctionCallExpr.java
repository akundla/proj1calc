package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public void staticallyCheck(List<VarDecl> declaredVars) {
        // TODO: later
    }

    @Override
    QuandaryValue eval(HashMap<String, QuandaryValue> variables) {
        FunctionDefinition func = Program.FunctionMap.get(this.identifier);
        if (func != null) {
            List<QuandaryValue> argVals = new ArrayList<QuandaryValue>();

            for (int i = 0; i < this.arguments.size(); i ++) {
                argVals.add(i, (QuandaryValue)this.arguments.get(i).eval(variables));
            }
            
            return func.exec(argVals);
        }
        else if (this.identifier.equals(RANDOM_INT_IDENT)) {
            return FunctionCallExpr.randomInt(
                // This had better be a QuandaryIntValue (else it is a statically incorrect program)
                (QuandaryIntValue)this.arguments.get(0).eval(variables)
            );
        }
        else if (this.identifier.equals(LEFT_IDENT)) {
            return FunctionCallExpr.left(
                FunctionCallExpr.nilChecker(
                    (QuandaryRefValue)this.arguments.get(0).eval(variables)
                )
            );
        }
        else if (this.identifier.equals(RIGHT_IDENT)) {
            return FunctionCallExpr.right(
                FunctionCallExpr.nilChecker(
                    (QuandaryRefValue)this.arguments.get(0).eval(variables)
                )
            );
        }
        else if (this.identifier.equals(ISATOM_IDENT)) {
            return FunctionCallExpr.isAtom(
                this.arguments.get(0).eval(variables)
            );
        }
        else if (this.identifier.equals(ISNIL_IDENT)) {
            return FunctionCallExpr.isNil(
                this.arguments.get(0).eval(variables)
            );
        }
        else if (this.identifier.equals(SETLEFT_IDENT)) {
            return FunctionCallExpr.setLeft(
                FunctionCallExpr.nilChecker(
                    (QuandaryRefValue)this.arguments.get(0).eval(variables)
                ),
                this.arguments.get(1).eval(variables)
            );
        }
        else if (this.identifier.equals(SETRIGHT_IDENT)) {
            return FunctionCallExpr.setRight(
                FunctionCallExpr.nilChecker(
                    (QuandaryRefValue)this.arguments.get(0).eval(variables)
                ),
                this.arguments.get(1).eval(variables)
            );
        }
        throw new RuntimeException("FunctionCallExpr was given an unrecognizable identifier.");
    }

    // int randomInt(int n) - Returns a random int in [0; n)
    private static QuandaryIntValue randomInt(QuandaryIntValue n) {
        long randomVal = (long)(n.value * Math.random());
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

    // int isAtom(Q x) – Returns 1 if x is nil or an int, and 0 otherwise (it is a Ref)
    private static QuandaryIntValue isAtom(QuandaryValue x) {
        if (x instanceof QuandaryIntValue) {
            return new QuandaryIntValue(1);
        }
        else if (x instanceof QuandaryRefValue) {
            QuandaryRefValue refX = (QuandaryRefValue)x;
            // Is x nil?
            if (refX.referencedQObject == null) {
                return new QuandaryIntValue(1);
            }
            else {
                return new QuandaryIntValue(0);
            }
        }
        else {
            throw new RuntimeException("QuandaryValue x passed to isAtom was neither instance of QuandaryIntValue nor QuandaryRefValue.");
        }
    }

    // int isNil(Q x) – Returns 1 if x is nil; returns 0 otherwise (it is an int or Ref)
    private static QuandaryIntValue isNil(QuandaryValue x) {
        if (x instanceof QuandaryIntValue) {
            return new QuandaryIntValue(0);
        }
        else if (x instanceof QuandaryRefValue) {
            QuandaryRefValue refX = (QuandaryRefValue)x;
            // Is x nil?
            if (refX.referencedQObject == null) {
                return new QuandaryIntValue(1);
            }
            else {
                return new QuandaryIntValue(0);
            }
        }
        else {
            throw new RuntimeException("QuandaryValue x passed to isAtom was neither instance of QuandaryIntValue nor QuandaryRefValue.");
        }
    }

    // mutable int setLeft(Ref r, Q value) – Sets the left field of the object referenced by r to value, and returns 1
    private static QuandaryIntValue setLeft(QuandaryRefValue r, QuandaryValue value) {
        FunctionCallExpr.checkTypes(r.referencedQObject.left, value);
        r.referencedQObject.left = value;
        return new QuandaryIntValue(1);
    }

    // mutable int setRight(Ref r, Q value) – Sets the right field of the object referenced by r to value, and returns 1
    private static QuandaryIntValue setRight(QuandaryRefValue r, QuandaryValue value) {
        FunctionCallExpr.checkTypes(r.referencedQObject.right, value);
        r.referencedQObject.right = value;
        return new QuandaryIntValue(1);
    }

    // Helper function to detect nil dereferences
    private static QuandaryRefValue nilChecker(QuandaryRefValue r) {
        // Should be impossible. 
        if (r == null)
            throw new NilDereferenceException("BIG PROBLEM: r itself is nil in left(), right(), setLeft(), or setRight().");
        // If you call left() on a nil ref the program is dynamically incorrect
        if (r.referencedQObject == null)
            throw new NilDereferenceException("Nil dereference error on built-in function left(), right(), setLeft(), or setRight().");
        return r;
    }

    // Enforces rule precluding heap mutation.
    private static void checkTypes(QuandaryValue field, QuandaryValue newVal) {
        if (field instanceof QuandaryIntValue) {
            if (!(newVal instanceof QuandaryIntValue))
                throw new DynamicCheckException("Cannot assign an int field in the heap to anything other than an int.");
        }
        else if (field instanceof QuandaryRefValue){
            if (!(newVal instanceof QuandaryRefValue))
                throw new DynamicCheckException("Cannot assign a Ref field in the heap to anything other than a Ref.");
        }
    }
}
