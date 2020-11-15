int main(int arg) {
    int i = 1776;
    Ref r = (16 . 19);
    /* Implicit upcast */
    Q q = r;
    
    return (Ref)spooky2();
}

Q spooky2() {
    return 1776;
}
