package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assertions.assertEquals(0, sum.intValue());
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
        Assertions.assertEquals(12, new Fraction(3).plus(new Fraction(9)).intValue());
    }

    public static class Fraction {
        private int integerValue;

        public Fraction(int integerValue) {
            this.integerValue = integerValue;
        }

        public Fraction plus(Fraction that) {
            if (this.integerValue == 0)
                return that;
            else if (that.integerValue == 0)
                return this;
            else
                return new Fraction(this.integerValue + that.integerValue);
        }

        public int intValue() {
            return integerValue;
        }
    }
}
