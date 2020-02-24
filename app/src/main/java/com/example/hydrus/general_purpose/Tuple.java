package com.example.hydrus.general_purpose;



public class Tuple<TUPLE_SIZE, TUPLE_ELEMENT> {

    private static void test() {
        Multiple<NaturalNumber.One, String> twoStrings = new Multiple<>("string 1", new Single<>("string 2"));
        ((Single<String>)twoStrings.getNext()).getElement();
        Multiple<NaturalNumber.Succ<NaturalNumber.One>, String> threeStrings = new Multiple<>("string 0", twoStrings);
        ((Multiple<NaturalNumber.One, String>)threeStrings.getNext()).getNext();
    }

    public static class Single<TUPLE_ELEMENT> extends Tuple<NaturalNumber.One, TUPLE_ELEMENT> {
        private final TUPLE_ELEMENT singleTupleElement;
        public Single(TUPLE_ELEMENT singleTupleElement) {
            this.singleTupleElement = singleTupleElement;
        }

        public static <TUPLE_ELEMENT> Single<TUPLE_ELEMENT> tupleOfSingleElement(TUPLE_ELEMENT singleTupleElement) {
            return new Single(singleTupleElement);
        }

        public TUPLE_ELEMENT getElement() {
            return this.singleTupleElement;
        }
    }

    public static class Multiple<ONE_LESS_THEN_TUPLE_SIZE extends NaturalNumber, TUPLE_ELEMENT> extends Tuple<NaturalNumber.Succ<ONE_LESS_THEN_TUPLE_SIZE>, TUPLE_ELEMENT> {
        private final TUPLE_ELEMENT firstTupleElement;
        private final Tuple<ONE_LESS_THEN_TUPLE_SIZE, TUPLE_ELEMENT> tupleTheFirstElementIsPrependedTo;

        public Multiple(TUPLE_ELEMENT firstTupleElement, Tuple<ONE_LESS_THEN_TUPLE_SIZE, TUPLE_ELEMENT> tupleToPrependTo) {
            this.firstTupleElement = firstTupleElement;
            this.tupleTheFirstElementIsPrependedTo = tupleToPrependTo;
        }

        public TUPLE_ELEMENT getFirstElement() {
            return this.firstTupleElement;
        }

        public Tuple<ONE_LESS_THEN_TUPLE_SIZE, TUPLE_ELEMENT> getNext() {
            return this.tupleTheFirstElementIsPrependedTo;
        }
    }



}
