package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

public class ConsumeAndDispatchCommandsTest {
    @Test
    void oneCommand() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        consumeAndDispatchCommands(new StringReader("::command 1::"), commandInterpreter);

        Mockito.verify(commandInterpreter).handleCommand("::command 1::");
    }

    @Test
    void noCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        consumeAndDispatchCommands(new StringReader(""), commandInterpreter);

        Mockito.verifyNoInteractions(commandInterpreter);
    }

    @Test
    void severalCommands() throws Exception {
        CommandInterpreter commandInterpreter = Mockito.mock(CommandInterpreter.class);

        consumeAndDispatchCommands(new StringReader("::command 1::\n::command 2::\n::command 3::"), commandInterpreter);

        InOrder inOrder = Mockito.inOrder(commandInterpreter);
        Arrays.asList("::command 1::", "::command 2::", "::command 3::").stream().forEachOrdered(
                each -> inOrder.verify(commandInterpreter).handleCommand(each)
        );
    }

    private void consumeAndDispatchCommands(Reader commandReader, CommandInterpreter commandInterpreter) throws IOException {
        new BufferedReader(commandReader).lines().forEachOrdered(commandInterpreter::handleCommand);
    }

    public interface CommandInterpreter {
        void handleCommand(String commandText);
    }
}
