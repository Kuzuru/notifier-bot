package urfu;

import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class Main {
  public static void main(String[] args) {
    System.out.println("[LOG] Bot started...\n");

    LinkedHashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    try {
      while (true) {
        System.out.print("notifier@bot: ");

        String userInput = input.readLine();

        // Обработка лишних пробелов во входной строке
        userInput = userInput.trim().replaceAll(" +", " ");
        String[] userInputArgs = userInput.split(" ");

        ICommand command = commands.get(userInputArgs[0]);

        if (command != null) command.execute(userInputArgs);
      }
    } catch (Exception e) {
      System.out.println("[ERR] Got exception while waiting user input: " + e.getMessage());
    }
  }
}
