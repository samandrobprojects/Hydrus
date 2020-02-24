package com.example.hydrus.general_purpose;

public class NaturalNumber {

    /*public int integerRepresentationOfNaturalNumber() {
        throw new UnsupportedOperationException("Natural number without valid construction cannot have integer representation");
    }*/

    public static class One extends NaturalNumber {
        /*public One() {}
        @Override
        public int integerRepresentationOfNaturalNumber() {
            return 1;
        }*/
    }

    public static class Succ<SUCCEEDED_NATURAL_VALUE extends NaturalNumber> extends NaturalNumber {
        /*private final SUCCEEDED_NATURAL_VALUE succeededNaturalValue;
        public Succ(SUCCEEDED_NATURAL_VALUE succeededNaturalValue) {
            this.succeededNaturalValue = succeededNaturalValue;
        }

        @Override
        public int integerRepresentationOfNaturalNumber() {
            return this.succeededNaturalValue.integerRepresentationOfNaturalNumber() + 1;
        }*/
    }
}

