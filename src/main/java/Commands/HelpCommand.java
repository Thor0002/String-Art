package Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


// Команда помощь
public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                        "Этот бот, который позволяет преобразовать получаемые изображения в String Art.\n" +
                        "Автор идеи - http://artof01.com/vrellis/works/knit.html\n" +
                        "Чтобы использовать нужно скинуть боту изображения в формате фото и он вернёт преобразованое.\n" +
                        "*Список команд*\n" +
                        "/quality - для того чтобы регулировать качество\n" +
                        "/register - регистрация профиля для того чтобы сохранять изображения\n" +
                        "/login - вход в свой профиль\n" +
                        "/save - сохранить предыдущее изображение в свой профиль\n" +
                        "/upload - загрузить сохранённое изображение\n" +
                        "/example - примеры работы бота\n " +
                        "/help - помощь\n"
                        );
    }
}
