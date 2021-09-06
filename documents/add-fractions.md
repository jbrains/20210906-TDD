# Add Fractions

Exact arithmetic. No floating points!

## Backwards compatible with integers
0 + 0 = 0      # THE KERNEL
6 + 0 = 6      # backwards compatible with integers
0 + 8 = 8
5 + 9 = 14
-2 + 5 = 7

## The rest
1/5 + 2/5 = 3/5     # denominators are the same
1/8 + 3/8 = 1/2     # reduce answer to "lowest terms": 1/2, not 4/8
1/4 + 3/4 = 1    # answer is an integer: 1, not 4/4, not 1/1
3/4 + 3/8 = 9/8    # one denominator is a multiple of the other one
2/7 + 3/4 = 29/28
3/5 + 0 = 3/5    # adding 0 does nothing
3/8 + (-1/8) = 1/4    # add negative numbers == subtraction

## Create Fraction Value

1/0 = ERROR    # what kind of error?! Exception? Maybe? Either? NaN?
-1/-2 = 1/2     # positive denominator by convention?
4/8 = 1/2     # remove common factors; reduce to lowest terms

## Other contracts
- equals
- hashCode

## toString

-1/-2 -> "1/2"
6/-11 -> "-6/11"
6/1 -> "6"
