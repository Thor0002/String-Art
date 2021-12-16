package Commands;

import java.util.HashMap;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import DataBase.UserService;

//Команда для входа в профиль
public class LoginCommand extends ServiceCommand {
	private HashMap<String, String> nickNames;


    public LoginCommand(String identifier, String description, HashMap<String, String> nickNames) {
        super(identifier, description);
        this.nickNames = nickNames;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        
        String answer;
        if(strings.length == 0) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, 
            		"Это команда позволяет войти в ваш аккаунт.\n" + 
                    "Чтобы войти нужно ввести команду /login Логин и Пароль.\n" +
                    "Например: \"/login Логин Пароль\".\n Либо:  \"/login Пароль\". Если вы регестрировались с помощью имени пользователя Telegram");
        } else if(strings.length == 1) {
        	String userPassword = strings[0];
        	boolean t = UserService.Login(userName, userPassword);
        	if (t) {
        		nickNames.put(userName, userName);
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,"Войти удалось.\n");
        	} else {
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, 
        				"Не удаётся войти. Пожалуйста, проверьте правильность написания логина и пароля.\n");
        	}
        } else if(strings.length == 2) {
        	String profileName = strings[0];
            String nickName = nickNames.get(userName);
        	if(nickName != null && nickName.equals(profileName)) {sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,"Вы уже вошли.\n"); return;}
        	
        	String userPassword = strings[1];
        	boolean t = UserService.Login(profileName, userPassword);
        	if (t) {
        		nickNames.put(userName, profileName);
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,"Войти удалось.\n");
        	} else {
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, 
        				"Не удаётся войти. Пожалуйста, проверьте правильность написания логина и пароля.\n");
        	}
        } else {
        	answer = "Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /login.\n";
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,answer);
        }
    }
}

