package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.stream.Stream;

public class PointOfSaleTerminal {
    void runTerminal(Reader commandReader, CommandInterpreter commandInterpreter, CanoncalizeCommand canoncalizeCommand) {
        readLinesOfText(commandReader)
                .map(canoncalizeCommand::canonicalizeCommand)
                .forEachOrdered(commandInterpreter::handleCommand);
    }

    Stream<String> readLinesOfText(Reader commandReader) {
        return new BufferedReader(commandReader).lines();
    }

    public static void main(String[] args) {
        new PointOfSaleTerminal().runTerminal(
                new InputStreamReader(System.in),
                new SellOneItemController(
                        new InMemoryCatalog(new HashMap<>() {{
                            put("12345", Price.euroCents(795));
                        }}),
                        new ConsoleDisplay()
                ),
                new TrimWhitespace());
    }
}