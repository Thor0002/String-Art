package Commands;

import java.util.HashMap;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import DataBase.UserService;

//Команда для сохранения изображения
public class SaveCommand extends ServiceCommand {
	private HashMap<String, String> nickNames;
	
    public SaveCommand(String identifier, String description, HashMap<String, String> nickNames) {
        super(identifier, description);
        this.nickNames = nickNames;
    }

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
		String userName = Utils.getUserName(user);
		
		if(strings.length == 0) {
			sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
					"Это команда позволяет сохранить предыдущее изображение в ваш профиль.\n" + 
                    "Чтобы сохранить предыдущее присланное вами преобразованое изображение в ваш профиль нужно " + 
					"ввести команду /save и Имя изображения, которое не содержить пробелов.\n" +
                    "Например: \"/save Имя\"");
		} else if(strings.length == 1) {
			String nickName = nickNames.get(userName);
			if(nickName == null) {sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,"Вы не вошли.\n"); return;}
			
			java.io.File ArtImage  = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Session\\" + nickName + ".jpg");
			if(!ArtImage.exists()) {
				sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
						"Вы не отправляли ещё изображений.\n");
				return;
			}
			String imageName = strings[0];
			String Saves;
			Saves = UserService.Save(nickName, imageName);
			sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, Saves);
		} else {
			sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
					"Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /register\n");
		}
	}
}

