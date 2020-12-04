mutable int main (int arg) {
    Ref r = (0 . nil);
    int x = [increment100times(r) + increment100times(r)];
    print(r);
    return x;
}

mutable int increment100times(Ref r) {
    mutable int i = 0;
    while (i < 100) {
        i = i + 1;
        setLeft(r, (int)left(r) + 1);
    }
    return (int)left(r);
}
