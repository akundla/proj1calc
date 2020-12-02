mutable int main (int arg) {
    if (arg == 1) {
        /* At size 16, -heapsize 408 runs successfully but size 384 fails */
        useHeapSize(16);
    }
    else if (arg == 2) {
        makeSelfRefObjs(1000);
    }
    else if (arg == 3) {
        
    }
    else if (arg == 4) {
        
    }
    else {
        print(arg);
    }
    return 1776;
}

mutable Ref useHeapSize(mutable int n) {
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

mutable int makeSelfRefObjs(mutable int n) {
    if (n < 0) {
        n = 0;
    }
    while (n > 0) {
        Ref a = (1 . nil);
        Ref b = (2 . nil);
        /*setRight(a, b);
        setRight(b, a);*/

        n = n - 1;
    }
    return 1776;
}
