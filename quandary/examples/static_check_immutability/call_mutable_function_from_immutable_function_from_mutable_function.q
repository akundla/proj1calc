int main(int arg){
    return 1;
}

mutable int mutableFunction1(){
	return immutableFunction();
}

int immutableFunction(){
    mutableFunction2();
    return 1;
}

mutable int mutableFunction2(){
    return 2;
}