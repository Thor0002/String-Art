package TelegramBot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Commands.ExampleCommand;
import Commands.HelpCommand;
import Commands.LoginCommand;
import Commands.QualityCommand;
import Commands.RegisterCommand;
import Commands.SaveCommand;
import Commands.StartCommand;
import Commands.UploadCommand;
import SpringArtAlgorithm.MakerStringArt;

@Component
public class StringArtBot extends TelegramLongPollingCommandBot  {
	private MakerStringArt modifier = new MakerStringArt();           // Формирует String Art
	private HashMap<String, String> nickNames = new HashMap<>();      // Имена вошедших профилей

	public StringArtBot() {
		super();
		register(new HelpCommand("help","Помощь"));
		register(new QualityCommand("quality","Качество", modifier));
		register(new RegisterCommand("register", "Регистрация"));
		register(new LoginCommand("login", "Вход", nickNames));
		register(new SaveCommand("save", "Сохранить изображение", nickNames)); 
		register(new UploadCommand("upload", "Загрузить изображение", nickNames));
		register(new StartCommand("start", "Старт"));
		register(new ExampleCommand("example", "Примеры"));
	}

	// Ответ бота на фото
	@Override
	public void processNonCommandUpdate(Update update) {
		if (update.hasMessage() && update.getMessage().hasPhoto()) {
			String userName = update.getMessage().getFrom().getUserName();

			List<PhotoSize> photos = update.getMessage().getPhoto();
			PhotoSize photoSize = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

			java.io.File output = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Image.jpg");

			String filePath;
			java.io.File photo;
			GetFile getFileMethod = new GetFile();
			getFileMethod.setFileId(photoSize.getFileId());
			try {
				File file = execute(getFileMethod);
				filePath = file.getFilePath();
				photo =  downloadFile(filePath);
				BufferedImage image = ImageIO.read(photo);
				ImageIO.write(image, "jpg", output);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			java.io.File modifiedImage = modifier.getStringArtImage(output);
			String nickName = nickNames.get(userName);
			java.io.File ArtImage;
			if(nickName == null) {
				ArtImage  = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Session\\" + userName + ".jpg");}
			else {
				ArtImage  = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Session\\" + nickName + ".jpg");
			}
			try {
				java.nio.file.Files.move(modifiedImage.toPath(),ArtImage.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			String chatId = update.getMessage().getChatId().toString();
			SendPhoto sendPhotoRequest = new SendPhoto();
			sendPhotoRequest.setChatId(chatId);
			sendPhotoRequest.setPhoto(new InputFile(ArtImage));
			try {
				execute(sendPhotoRequest);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public String getBotUsername() {
		return "@StringArtBotRu";
	}

	@Override
	public String getBotToken() {
		return "********";
	}


}
