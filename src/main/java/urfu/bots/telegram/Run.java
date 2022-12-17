package urfu.bots.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import urfu.core.commands.init.CommandInitializer;
import urfu.core.commands.init.ICommand;
import urfu.core.utils.StdoutLocker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class Run extends TelegramLongPollingBot
{
    private String handleShowHelpCallback(CallbackQuery callbackQuery)
    {
        log.atDebug().log("callbackQuery: {}", callbackQuery.getMessage().getText());

        int startIndex = "Команде ".length();
        int endIndex = callbackQuery.getMessage().getText().indexOf(" ", startIndex);
        String parsedCommandName = callbackQuery.getMessage().getText().substring(startIndex, endIndex);

        HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();
        ICommand helpCommand = commands.get("help");

        return StdoutLocker.lockStdout(() -> {
            helpCommand.safeArgsExecute(new String[]{"/help", parsedCommandName});
        });
    }

    public void onUpdateReceived(Update update)
    {
        if (update.hasCallbackQuery()) {
            // This update contains a callback query
            CallbackQuery callbackQuery = update.getCallbackQuery();

            // Get the callback data and the message that triggered the callback
            String callbackData = callbackQuery.getData();
            Message message = callbackQuery.getMessage();

            SendMessage response = new SendMessage();

            // Use the callback data and message to handle the callback as needed
            if (callbackData.equals("show_help_callback")) {
                response.setChatId(message.getChatId());
                response.setText(handleShowHelpCallback(callbackQuery));
            } else {
                response.setChatId(message.getChatId());

                String output = StdoutLocker.lockStdout(() ->
                {
                    System.out.println("UNKNOWN CALLBACK: " + callbackQuery);
                });

                response.setText(output);
            }

            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message != null && message.hasText()) {
                SendMessage response = new SendMessage();
                response.setText(message.getText());
                response.setChatId(message.getChatId());

                if (message.isCommand()) {
                    commandReply(message);
                    return;
                }

                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public InlineKeyboardMarkup helpInlineBuilder(String command)
    {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton btn = new InlineKeyboardButton("Show " + command + " help");
        btn.setCallbackData("show_help_callback");
        buttons.add(btn);

        List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
        buttonRows.add(buttons);

        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(buttonRows);

        return inlineKeyboard;
    }


    public void commandReply(Message message)
    {
        HashMap<String, ICommand> commands = CommandInitializer.getAvailableCommands();
        SendMessage response = new SendMessage();

        String userInput = message.getText().replaceFirst("^/", "");
        userInput = userInput.trim().replaceAll(" +", " ");
        String[] userInputArgs = userInput.split(" ");

        ICommand command = commands.get(userInputArgs[0]);

        log.atDebug().log("Пришла команда: {}", userInputArgs[0]);
        log.atDebug().log("Аргументы: {}", (Object) userInputArgs);

        String msg = StdoutLocker.lockStdout(() ->
        {
            boolean isSucceed = false;

            if (command != null)
                isSucceed = command.safeArgsExecute(userInputArgs);

            if (!isSucceed) {
                InlineKeyboardMarkup helpKeyboard = helpInlineBuilder(userInputArgs[0]);
                response.setReplyMarkup(helpKeyboard);
            }
        });

        if (msg.isEmpty())
            msg = "Я не знаю такой команды :(";

        response.setText(msg);
        response.setChatId(message.getChatId());

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername()
    {
        return "Notifier";
    }

    public String getBotToken()
    {
        return System.getProperty("APP_KEY");
    }
}
