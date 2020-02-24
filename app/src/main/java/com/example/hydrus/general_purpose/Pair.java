package com.example.hydrus.general_purpose;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class Pair<FIRST_OBJECT, SECOND_OBJECT> {

    private final FIRST_OBJECT _firstObject;
    private final SECOND_OBJECT _secondObject;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static <FIRST_OBJECT, SECOND_OBJECT> Pair<FIRST_OBJECT, SECOND_OBJECT> pairWithFirstAndSecondObjects(FIRST_OBJECT firstObject, SECOND_OBJECT secondObject) {
        return new Pair<FIRST_OBJECT, SECOND_OBJECT>(firstObject, secondObject);
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public FIRST_OBJECT getFirst() {
        return _firstObject;
    }

    public SECOND_OBJECT getSecond() {
        return _secondObject;
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private Pair(FIRST_OBJECT firstObject, SECOND_OBJECT secondObject) {
        super();
        _firstObject = firstObject;
        _secondObject = secondObject;
    }
}
