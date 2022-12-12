package urfu;

import lombok.extern.slf4j.Slf4j;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;
import urfu.core.config.ConfigInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

@Slf4j
public class Main {
  @SuppressWarnings("InfiniteLoopStatement")
  public static void main(String[] args) {
    log.atInfo().log("Bot starting up...");

    log.atInfo().log("Loading environment variables...");
    ConfigInitializer.load();

    log.atInfo().log("Loading commands list...");
    HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    log.atInfo().log("Successfully started up!");
    try {
      while (true) {
        System.out.print("notifier@bot: ");

        String userInput = input.readLine();
        if (userInput == null) continue;

        // Обработка лишних пробелов во входной строке
        userInput = userInput.trim().replaceAll(" +", " ");
        String[] userInputArgs = userInput.split(" ");

        ICommand command = commands.get(userInputArgs[0]);

        if (command != null) command.safeArgsExecute(userInputArgs);
      }
    } catch (Exception e) {
      log.atError().log("Got exception while waiting user input");
      log.atError().log("ERRMSG: " + e.getMessage());
    }
  }
}
