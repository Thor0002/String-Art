package Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class SaveCommand extends ServiceCommand {

    public SaveCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                        "Это команда позволяет сохранить предыдущее изображение в ваш аккаунт.\n");
    }
}

