package urfu;

import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("[LOG] Bot started...\n");

        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                System.out.print("notifier@bot: ");

                String userInput = INPUT.readLine();

                userInput = userInput.trim().replaceAll(" +", " ");
                String[] userInputArgs = userInput.split(" ");

                ICommand command = COMMANDS.get(userInputArgs[0]);

                if (command != null)
                    command.execute(userInputArgs);
            }
        } catch (Exception e) {
            System.out.println("[ERR] Got exception while waiting user input: " + e.getMessage());
        }
    }
}