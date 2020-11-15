int main(int arg) {    
    Ref r = ((17 . 76) . (16 . 19));
    int i = 1619;

    /* Should fail at runtime, can't set an int slot to a ref */
    setRight(r, i);
    return r;
}
