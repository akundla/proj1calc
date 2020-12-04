int main (int arg) {
    Ref r1 = (1 . 2);
    Ref r2 = (3 . nil);

    /* If r1 is nil */
    if (isNil(r1) != 0) {
        /* Should not print */
        print(88);
    }
    else {
        /* Should print */
        print(99);
    }

    /* If r2 is nil */
    if (isNil(r2) != 0){
        /* Should NOT print */
        print(55);
    }
    else {
        /* Should print */
        print(11);

        if (isNil(right(r2)) != 0) {
            /* Should print */
            print(44);
        }
        else {
            /* Should not print */
            print(33);
        }
    }

    if (isNil(r2) != 0)
        return 1776;
    if (isNil(right(r2)) != 0)
        return 1812;
    return 1789;
}
