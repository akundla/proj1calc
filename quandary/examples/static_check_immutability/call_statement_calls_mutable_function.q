mutable int main(int arg){
    int numberOfTimesToCall = 20;
    callMutableFunctionTwentyTimes(numberOfTimesToCall);
    return arg;
}

mutable int callMutableFunctionTwentyTimes(int numTimesToCall){
    if (numTimesToCall == 0) return 1;
    
    callMutableFunctionTwentyTimes(numTimesToCall -1);

    return 1;
}
