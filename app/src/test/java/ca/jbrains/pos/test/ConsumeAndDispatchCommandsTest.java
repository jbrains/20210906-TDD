package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class ConsumeAndDispatchCommandsTest {
    @Test
    void oneCommand() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        readLinesOfTextThenCanonicalizeThemIntoCommandsThenDispatchCommands(new StringReader("::command 1::"), commandInterpreter);

        Mockito.verify(commandInterpreter).handleCommand("::command 1::");
    }

    @Test
    void noCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        readLinesOfTextThenCanonicalizeThemIntoCommandsThenDispatchCommands(new StringReader(""), commandInterpreter);

        Mockito.verifyNoInteractions(commandInterpreter);
    }

    @Test
    void severalCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        readLinesOfTextThenCanonicalizeThemIntoCommandsThenDispatchCommands(new StringReader("::command 1::\n::command 2::\n::command 3::"), commandInterpreter);

        InOrder inOrder = Mockito.inOrder(commandInterpreter);
        Arrays.asList("::command 1::", "::command 2::", "::command 3::").stream().forEachOrdered(
                each -> inOrder.verify(commandInterpreter).handleCommand(each)
        );
    }

    @Test
    void severalCommandsWithInsignificantWhitespace() {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        readLinesOfTextThenCanonicalizeThemIntoCommandsThenDispatchCommands(new StringReader(
                "::canonical command 1::\n" +
                        "    ::command with leading spaces::\n" +
                        "::command with trailing spaces::\n" +
                        "::canonical command 2::\n" +
                        "    \n" +
                        "      ::command with leading and trailing spaces::    \n" +
                        "\t    \r::command with exotic whitespace::\f   \t\t\n" +
                        "::canonical command 3::\n" +
                        "      "), commandInterpreter);

        InOrder inOrder = Mockito.inOrder(commandInterpreter);
        Arrays.asList(
                "::canonical command 1::",
                "::command with leading spaces::",
                "::command with trailing spaces::",
                "::canonical command 2::",
                "",
                "::command with leading and trailing spaces::",
                "::command with exotic whitespace::",
                "::canonical command 3::",
                ""
        ).stream().forEachOrdered(
                each -> inOrder.verify(commandInterpreter).handleCommand(each)
        );
    }

    private void readLinesOfTextThenCanonicalizeThemIntoCommandsThenDispatchCommands(Reader commandReader, CommandInterpreter commandInterpreter) {
        dispatchCommands(commandInterpreter, readLinesOfText(commandReader).map(this::canonicalizeLineIntoCommand));
    }

    private String canonicalizeLineIntoCommand(String line) {
        return line.trim();
    }

    private void dispatchCommands(CommandInterpreter commandInterpreter, Stream<String> commands) {
        commands.forEachOrdered(commandInterpreter::handleCommand);
    }

    private Stream<String> readLinesOfText(Reader commandReader) {
        return new BufferedReader(commandReader).lines();
    }

    public interface CommandInterpreter {
        void handleCommand(String commandText);
    }
}
