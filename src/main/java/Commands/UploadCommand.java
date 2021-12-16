package Commands;

import java.util.HashMap;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import DataBase.UserService;

//Команда для загрузки изображения из профиля
public class UploadCommand extends ServiceCommand {
    private HashMap<String, String> nickNames;
    
    public UploadCommand(String identifier, String description, HashMap<String, String> nickNames) {
        super(identifier, description);
        this.nickNames = nickNames;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        if(strings.length == 0) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Это команда позволяет загружать сохранённые изображения из вашего профиля.\n"+ 
                    "Чтобы загрузить сохранённые изображения из вашего профиля нужно ввести команду /upload и Имя изображения.\n"+
                    "Например: \"/upload Имя\"");
        }  else if(strings.length == 1){
        	String nickName = nickNames.get(userName);
			if(nickName == null) {sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,"Вы не вошли.\n"); return;}
			
            String imageName = strings[0];
            boolean isThereImage = UserService.Upload(nickName, imageName);
            if(!isThereImage) {
            	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, "Такого изображения нет.\n"); return;
            }
            
            java.io.File Example = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Images\\"+ nickName + "\\" + imageName + ".jpg");
            String chatId = chat.getId().toString();
            SendPhoto sendPhotoRequest = new SendPhoto();
    		sendPhotoRequest.setChatId(chatId);
    		sendPhotoRequest.setPhoto(new InputFile(Example));
    		try {
				absSender.execute(sendPhotoRequest);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /upload\n");
        }

    }
}

