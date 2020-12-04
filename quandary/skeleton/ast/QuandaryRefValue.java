package ast;

import java.util.concurrent.atomic.AtomicBoolean;

public class QuandaryRefValue extends QuandaryValue {
    // If this is null, then it represents the value nil;
    public QuandaryObject referencedQObject;
    private AtomicBoolean isLocked = new AtomicBoolean(false);

    public QuandaryRefValue(QuandaryObject qObj) {
        this.referencedQObject = qObj;
    }

    @Override
    public String toString() {
        if (this.referencedQObject == null) {
            return "nil";
        }
        else {
            return referencedQObject.toString();
        }
    }

    public void acquireLock() {
        while (!this.isLocked.compareAndSet(false, true)) { }
    }

    public void releaseLock() {
        // Since a thread releasing a lock which it does not have has undefined semantics, the fact that we do not check ownership here is fine. 
        this.isLocked.set(false);
    }
}
