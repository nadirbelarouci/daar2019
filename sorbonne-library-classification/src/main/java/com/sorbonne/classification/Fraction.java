package com.sorbonne.classification;

public class Fraction {
    int denominator = 0, numerator = 0;

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void addToDenominator(int value) {
        denominator += value;
    }

    public void addToNumerator(int value) {
        numerator += value;
    }

    public double divide() {
        return (double) numerator / denominator;
    }
}
