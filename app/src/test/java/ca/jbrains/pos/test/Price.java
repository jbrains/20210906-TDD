package ca.jbrains.pos.test;

public record Price(int centsValue) {
    public static Price euroCents(int centsValue) {
        return new Price(centsValue);
    }
}
