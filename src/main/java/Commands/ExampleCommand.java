package Commands;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ExampleCommand extends ServiceCommand {

	public ExampleCommand(String identifier, String description) {
		super(identifier, description);
	}

	@Override
	public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

		java.io.File Example = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Example.jpg");

		String chatId = chat.getId().toString();
		SendPhoto sendPhotoRequest = new SendPhoto();
		sendPhotoRequest.setChatId(chatId);
		sendPhotoRequest.setPhoto(new InputFile(Example));
		try {
			absSender.execute(sendPhotoRequest);

		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
