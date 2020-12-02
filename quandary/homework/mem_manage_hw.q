mutable int main (int arg) {
    if (arg == 1) {
        /* At size 16 with both flavors of MarkSweep, -heapsize 408 runs successfully but size 384 fails */
        OOMforMarkSweep(16);
    }
    else if (arg == 2) {
        /* Goes OOM for RefCount but not MarkSweep or MarkSweepVerbose */
        OOMforRefCount(1000);
    }
    else if (arg == 3) {
        /* Will go OOM for MarkSweep and MarkSweepVerbose at -heapsize 384, but will be fine with Explicit */
        OOMforMarkSweepButNotExplicit(20);
    }
    else if (arg == 4) {
        /* At size 12, both -heapsize 408 AND 384 work with MarkSweep, but Explicit goes OOM */
        OOMforRefCount(12);
    }
    else {
        print(arg);
    }
    return 1776;
}

/* Grows one massive list with n elements, and maintains a pointer to it.
    Will not be GC'd by MarkSweep, OR Explicit */
mutable Ref OOMforMarkSweep(mutable int n) {
    Ref r = (nil . nil);
    mutable Ref temp = r;
    if (n < 0) {
        n = 0;
    }
    while (n > 0) {
        setRight(temp, (nil . nil));
        temp = (Ref)right(temp);

        n = n - 1;
    }
    return r;
}

/* Makes a bunch of self-referencing objects and orphans them, leaving them unreachable.
    Will not be GC'd by RefCount or Explicit but WILL be GC'd by MarkSweep */
mutable int OOMforRefCount(mutable int n) {
    if (n < 0) {
        n = 0;
    }
    while (n > 0) {
        Ref a = (1 . nil);
        Ref b = (2 . nil);
        setRight(a, b);
        setRight(b, a);

        n = n - 1;
    }
    return 1776;
}

/* Grows one massive list with n elements, and maintains a pointer to it,
    but explicitly frees the last element each time the next is created.
    Will not be GC'd by MarkSweep, but WILL BE by Explicit */
mutable Ref OOMforMarkSweepButNotExplicit(mutable int n) {
    Ref r = (nil . nil);
    mutable Ref temp = r;
    if (n < 0) {
        n = 0;
    }
    while (n > 0) {
        setRight(temp, (nil . nil));
        Ref oldTemp = temp;
        temp = (Ref)right(temp);
        free(oldTemp);

        n = n - 1;
    }
    return r;
}
