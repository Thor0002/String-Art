package Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import DataBase.UserService;

//Команда для регистрации
public class RegisterCommand extends ServiceCommand {

    public RegisterCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        
        if(strings.length == 0) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Это команда позволяет регистрировать ваш профиль.\n" + 
                    "Чтобы зарегестрироваться нужно ввести команду /register Логин и Пароль, которые не должны содержать пробелов.\n" +
                    "Например: \"/register Логин Пароль\".\n Либо:  \"/register Пароль\". Тогда логином будет ваше имя пользователя Telegram");
        } else if(strings.length == 1) {
        	String userPassword = strings[0];
        	String log = UserService.Registration(userName, userPassword);
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, log);
        }else if(strings.length == 2) {
        	String nickName = strings[0];
        	String userPassword = strings[1];
        	String log = UserService.Registration(nickName, userPassword);
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, log);
        } else {
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        			"Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /register\n");
        }
    }
}

