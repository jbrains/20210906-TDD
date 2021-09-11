package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.Reader;
import java.io.StringReader;

public class ConsumeAndDispatchCommandsTest {
    @Test
    void oneCommand() {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        consumeAndDispatchCommands(new StringReader("::command 1::"), commandInterpreter);

        Mockito.verify(commandInterpreter).handleCommand("::command 1::");
    }

    private void consumeAndDispatchCommands(Reader commandReader, CommandInterpreter commandInterpreter) {
        commandInterpreter.handleCommand("::command 1::");
    }

    public interface CommandInterpreter {
        void handleCommand(String commandText);
    }
}
