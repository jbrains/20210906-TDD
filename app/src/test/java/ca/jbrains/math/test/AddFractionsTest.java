package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() {
        Assertions.assertEquals(new Fraction(0), new Fraction(0).plus(new Fraction(0)));
    }

    @Test
    void notZeroPlusZero() {
        Assertions.assertEquals(new Fraction(4), new Fraction(4).plus(new Fraction(0)));
    }

    @Test
    void zeroPlusNotZero() {
        Assertions.assertEquals(new Fraction(7), new Fraction(0).plus(new Fraction(7)));
    }

    @Test
    void anyIntegers() {
        Assertions.assertEquals(new Fraction(-3), new Fraction(8).plus(new Fraction(-11)));
    }

    @Test
    void sameDenominatorsWithNoReducing() {
        Assertions.assertEquals(
                new Fraction(3, 5),
                new Fraction(1, 5).plus(new Fraction(2, 5)));
    }

    @Test
    void sameDenominatorsWithReducing() {
        Assertions.assertEquals(
                new Fraction(2),
                new Fraction(1, 2).plus(new Fraction(3, 2)));
    }

    @Test
    void denominatorsAreRelativelyPrime() {
        Assertions.assertEquals(
                new Fraction(25, 28),
                new Fraction(1, 7).plus(new Fraction(3, 4)));
    }

    @Test
    void denominatorsHaveACommonFactor() {
        Assertions.assertEquals(
                new Fraction(17, 30),
                new Fraction(1, 15).plus(new Fraction(3, 6)));
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
            if (this.denominator == that.denominator)
                return new Fraction(
                        this.numerator * that.denominator + that.numerator * this.denominator,
                        this.denominator * that.denominator);
            else
                return new Fraction(
                        this.numerator * that.denominator + that.numerator * this.denominator,
                        this.denominator * that.denominator);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator == that.numerator * this.denominator;
            } else {
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
    }
}
