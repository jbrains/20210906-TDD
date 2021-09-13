package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsumeAndDispatchCommandsTest {
    @Test
    void oneCommand() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        runTerminal(new StringReader("::command 1::"), commandInterpreter, this::canonicalizeLineIntoCommand);

        Mockito.verify(commandInterpreter).handleCommand("::command 1::");
    }

    @Test
    void noCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        runTerminal(new StringReader(""), commandInterpreter, this::canonicalizeLineIntoCommand);

        Mockito.verifyNoInteractions(commandInterpreter);
    }

    @Test
    void severalCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        runTerminal(new StringReader("::command 1::\n::command 2::\n::command 3::"), commandInterpreter, this::canonicalizeLineIntoCommand);

        InOrder inOrder = Mockito.inOrder(commandInterpreter);
        Arrays.asList("::command 1::", "::command 2::", "::command 3::").stream().forEachOrdered(
                each -> inOrder.verify(commandInterpreter).handleCommand(each)
        );
    }

    @Test
    void canonicalizeCommands() {
        List<String> lines = Arrays.asList(
                "::canonical command 1::",
                "    ::command with leading spaces::",
                "::command with trailing spaces::",
                "::canonical command 2::",
                "    ",
                "      ::command with leading and trailing spaces::    ",
                "\t    \r::command with exotic whitespace::\f   \t\t",
                "::canonical command 3::",
                "      "
        );

        Stream<String> canonicalCommands = lines.stream().map(this::canonicalizeLineIntoCommand);

        List<String> expectedCommands = Arrays.asList(
                "::canonical command 1::",
                "::command with leading spaces::",
                "::command with trailing spaces::",
                "::canonical command 2::",
                "",
                "::command with leading and trailing spaces::",
                "::command with exotic whitespace::",
                "::canonical command 3::",
                ""
        );

        Assertions.assertEquals(expectedCommands, canonicalCommands.collect(Collectors.toList()));
    }

    private void runTerminal(Reader commandReader, CommandInterpreter commandInterpreter, CanoncalizeCommand canoncalizeCommand) {
        readLinesOfText(commandReader)
                .map(canoncalizeCommand::canonicalizeCommand)
                .forEachOrdered(commandInterpreter::handleCommand);
    }

    public interface CanoncalizeCommand {
        String canonicalizeCommand(String rawLine);
    }

    private String canonicalizeLineIntoCommand(String line) {
        return line.trim();
    }

    private Stream<String> readLinesOfText(Reader commandReader) {
        return new BufferedReader(commandReader).lines();
    }

    public interface CommandInterpreter {
        void handleCommand(String commandText);
    }
}
