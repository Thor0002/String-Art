package Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class UploadCommand extends ServiceCommand {

    public UploadCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                        "Это команда позволяет загружать сохранённые изображения из вашего аккаунт.\n");
    }
}

