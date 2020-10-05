
/* Given any Q, returns a nonzero value if it is a list and 0 if it is not */
int isList(Q obj) {
    /* If object nil or int */
    if (isAtom(obj) != 0) {
        /* if obj nil it a list */
        if (isNil(obj) != 0) {
            /* nil a list */
            return 1;
        }
        else {
            /* Object must be an int, thus this not a list */
            return 0;
        }
    }
    /* Object is a ref */
    else {
        /* if this obj is a ref and its right side is a list, then obj is a list */
        Ref refObj = (Ref)obj;
        return isList(right(refObj));
    }
    /* Unreachable code, return is to make quandary static checker happy */
    return 0;
}

/* takes as input two lists and returns a new list
    that contains the first list’s elements followed by the second list’s elements.
    You can assume the inputs are lists. */
Ref append(Ref firstList, Ref secondList) {
    if (isNil(firstList) != 0)
        return secondList;
    else {
        /* Cast to Ref is safe because the right side of a list is always a list, unless the list is nil */
        return (left(firstList) . append((Ref)right(firstList), secondList));
    }
    /* Static type checking... */
    return nil;
}

/* takes as input a list and returns a list with the same elements,
    but in reverse order. */
Ref reverse(Ref list) {
    if (isNil(list) != 0) {
        return list;
    }
    else {
        /* (left(list) . nil) works because when you append to nil,
            you just replace it outright, so the only nil left is the last one
            which you want anyways because you need it to terminate the list */
        return append(reverse((Ref)right(list)), (left(list) . nil));
    }
    /* Static type checking... */
    return nil;
}



int main(int arg) {
    Q list1 = nil;
    Q list2 = (5 . nil);
    Q list2point5 = (4 . nil);
    Q list3 = (5 . (4 . nil));
    Q list4 = (1 . (2 . (3 . (4 . (5 . nil)))));
    Q notList1 = 5;
    Q notList2 = (5 . 5);
    
    print(list1);
    print(list2);
    print(list3);
    print(notList1);
    print(notList2);

    print(isList(list1));
    print(isList(list2));
    print(isList(list3));
    print(isList(notList1));
    print(isList(notList2));

    print(append((Ref)list2, (Ref)list2point5));
    print(list3);

    print(reverse((Ref)list3));

    print(isList(list4));
    print(reverse((Ref)list4));

    return 0;
}