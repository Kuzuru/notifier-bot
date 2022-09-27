package urfu;

import urfu.core.CommandInitializer;
import urfu.core.ICommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        System.out.println("[LOG] Bot started...\n");
        HashMap<String, ICommand> COMMANDS = CommandInitializer.getAvailableCommands();

        BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("notifier@bot $ ");

            // TODO: Надо убедиться, что пользовательский ввод никак и ничего не сможет сломать
            String userInput = INPUT.readLine();

            ICommand command = COMMANDS.get(userInput);

            if (command != null) {
                command.execute();
            }
        }
    }
}