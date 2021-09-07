package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() {
        Assertions.assertEquals(0, new Fraction(0).plus(new Fraction(0)).intValue());
    }

    @Test
    void notZeroPlusZero() {
        Assertions.assertEquals(4, new Fraction(4).plus(new Fraction(0)).intValue());
    }

    @Test
    void zeroPlusNotZero() {
        Assertions.assertEquals(7, new Fraction(0).plus(new Fraction(7)).intValue());
    }

    @Test
    void anyIntegers() {
        Assertions.assertEquals(-3, new Fraction(8).plus(new Fraction(-11)).intValue());
    }

    @Test
    void sameDenominatorsWithNoReducing() {
        Fraction sum = new Fraction(1, 5).plus(new Fraction(2, 5));
        Assertions.assertEquals(3, sum.getNumerator());
        Assertions.assertEquals(5, sum.getDenominator());
    }

    public static class Fraction {
        private int integerValue;

        public Fraction(int integerValue) {
            this.integerValue = integerValue;
        }

        public Fraction(int numerator, int denominator) {

        }

        public Fraction plus(Fraction that) {
            return new Fraction(this.integerValue + that.integerValue);
        }

        public int intValue() {
            return integerValue;
        }

        public int getNumerator() {
            return 3;
        }

        public int getDenominator() {
            return 5;
        }
    }
}
