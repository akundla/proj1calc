int main(int arg) {    
    Ref r = (1 . 2);
    Ref r1 = (2 . 3);

    /* Should fail at runtime, can't set an int slot to a ref */
    setLeft(r, r1);
    return r;
}
