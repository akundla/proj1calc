int main(int arg) {
    int i = 1776;
    Ref r = (16 . 19);
    /* Implicit upcast */
    Q q = r;
    /* Explicit downcast */
    Q obj = (Ref) i;
    return obj;
}
