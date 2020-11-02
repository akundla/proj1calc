Ref main(int arg) {
    mutable Ref r = (1619 . 1619);
    r = getARef();
    return r;
}

Ref getList(int arg) {
    if (arg <= 1)
        return (1 . nil);
    return (arg . getList(arg - 1));
}


Ref getARef() {
    return (1776 . 1776);
}
