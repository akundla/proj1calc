int main(int arg){
    Q someIntQ = 1;
    Q someRefQ = 2 . 3;
    Ref someRef = 3 . 4;
    int someInt = 5;
    return someFunction(someIntQ, someRefQ, someRef, someRef, someInt, someInt);
}

int someFunction(Q someIntQ,Q someRefQ,Q someRef1, Ref someRef2, Q someInt1, int someInt2){
    return someInt2;
}