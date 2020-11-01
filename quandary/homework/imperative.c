#include <stdlib.h>
#include <stdio.h>

void fib(int n, int * result);

// I recommend using my main function to test
// To compile: gcc ./imperative.c -o imperative
// To get the nth fibonacci number: ./imperative n
/*
int main(int argc, char** argv) {
    int n, result;
    if (argc != 2) {
        printf("Must give one int parameter as a command-line arg.");
    }
    else {
        n = atoi(argv[1]);
        fib(n, &result);
        printf("Result = %d\n", result);
        return result;
    }
}
*/

int main() {
    int result;
    fib(3, &result);
    printf("Result = %d\n", result);
}

void fib(int n, int * result) {
    int res1, res2;
    if (n < 2) {
        *result = n;
    }
    else {
        fib(n - 1, &res1);
        fib(n - 2, &res2);
        *result = res1 + res2;
    }
}