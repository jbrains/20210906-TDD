package ca.jbrains.pos.test;

public class TrimWhitespace implements CanoncalizeCommand {
    public String canonicalizeCommand(String line) {
        return line.trim();
    }
}