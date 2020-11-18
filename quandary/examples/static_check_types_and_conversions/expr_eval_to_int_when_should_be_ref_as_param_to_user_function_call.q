int main(int arg){
    return getIntFromRef(getInt(arg));
}

int getIntFromRef(Ref leftSideIsInt){
    return left(leftSideIsInt);
}

int getInt(int toReturn){
   return toReturn;
}