int main(int arg) {
    return getList(arg);
}

Ref getList(int arg) {
    if (arg <= 1)
        return (1 . nil);
    return (arg . getList(arg - 1));
}
