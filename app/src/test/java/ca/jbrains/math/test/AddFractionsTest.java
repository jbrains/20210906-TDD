package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

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

    @Test
    void rejectZeroDenominator() {
        try {
            new Fraction(7, 0);
            Assertions.fail("How did you create a Fraction with a 0 denominator?!");
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    void equalsInSpiteOfDifferentNumeratorAndDenominator() {
        Assertions.assertEquals(new Fraction(1, 4), new Fraction(4, 16));
        Assertions.assertEquals(new Fraction(1, 4), new Fraction(1, 4));
        Assertions.assertEquals(new Fraction(1, -4), new Fraction(-1, 4));
        Assertions.assertEquals(new Fraction(1, 4), new Fraction(-1, -4));
    }

    public static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(int integerValue) {
            this(integerValue, 1);
        }

        public Fraction(int numerator, int denominator) {
            if (denominator == 0)
                throw new IllegalArgumentException("Denominator can't be 0.");

            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(Fraction that) {
            return new Fraction(this.numerator + that.numerator, this.denominator);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator == that.numerator * this.denominator;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(numerator, denominator);
        }

        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }

        public int intValue() {
            return numerator;
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }
    }
}
