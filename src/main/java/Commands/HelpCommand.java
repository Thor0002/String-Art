package Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Помощь"
 */
public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                        "Этот бот, который позволяет преобразовать получаемые изображения в String Art.\n" +
                        "Примеры - http://artof01.com/vrellis/works/knit.html\n" +
                        "Чтобы использовать нужно скинуть боту изображения в формате фото и он вернёт новое.\n" +
                        "*Список команд*\n" +
                        "/quality - для того чтобы регулировать качество\n" +
                        "/register - регистрация аккаунта для того чтобы сохранять изображения\n" +
                        "/login - вход в свой аккаунт\n" +
                        "/save - сохранить предыдущее изображение в свой аккаунт\n" +
                        "/upload - загрузить сохранённую фотографию\n" +
                        "/example - примеры\n " +
                        "/help - помощь\n"
                        );
    }
}
