package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ConsumeAndDispatchCommandsTest {
    @Test
    void oneCommand() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        consumeAndDispatchCommands(new StringReader("::command 1::"), commandInterpreter);

        Mockito.verify(commandInterpreter).handleCommand("::command 1::");
    }

    private void consumeAndDispatchCommands(Reader commandReader, CommandInterpreter commandInterpreter) throws IOException {
        commandInterpreter.handleCommand(new BufferedReader(commandReader).readLine());
    }

    public interface CommandInterpreter {
        void handleCommand(String commandText);
    }
}
