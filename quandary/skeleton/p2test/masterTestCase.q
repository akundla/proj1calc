int layered_one() {
    print layered_two();
    return 320;
}

int layered_two() {
    print layered_three();
    return 160;
}

int layered_three() {
    return 80;
}

int wowowowow(int h, int m, int wooooooooow) {
    return randomInt(10);
}

int main(int x) {
    print x;
    print masterpiece(3, 4, 5, 5, 6);
    int variable = 315;
    if(5 < 10) {
        print 20;
        if(50000 < 50) {
            print 50;
        } else {
            print 40;
        }
    }
    print layered_one();
    int wow = 325;
    print (variable + wow);
    print variable + wow + 640;
    int recursion_result = recursion_funct((variable + wow + 640) * 2);
    /* print handletwo(100); */
    return wowowowow(1, 2, 3);
}

int masterpiece(int q, int a, int b, int c, int w) {
    return 10;
}

int handletwo(int varar) {
    if(varar < 50) {
        return 100;
    } else {
        print 999999;
        return 150;
    }
    return 101;
}

int recursion_funct(int wow) {
    print wow;
    if(wow >= 20480) {
        return wow;
    } 
    return recursion_funct(wow * 2);
}
