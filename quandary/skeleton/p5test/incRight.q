mutable int main (int arg) {
    Ref r = (0 . nil);
    Ref dummy = [increment100times(r) . increment100times(r)];
    return (int)left(r);
}

mutable int increment100times(Ref r) {
    mutable int i = 0;
    while (i < 100) {
        i = i + 1;
        acq(r);
        setLeft(r, (int)left(r) + 1);
        rel(r);
    }
    return 1776;
}
