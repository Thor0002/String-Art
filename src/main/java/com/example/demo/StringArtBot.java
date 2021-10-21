package com.example.demo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StringArtBot extends TelegramLongPollingBot{

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
			message.setChatId(update.getMessage().getChatId().toString());
			message.setText(update.getMessage().getText());
			
			System.out.println(update.getMessage().getFrom().getUserName());

			try {
				execute(message); // Call method to send the message
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		if (update.hasMessage() && update.getMessage().hasPhoto()) {
		    // Array with photos
		    List<PhotoSize> photos = update.getMessage().getPhoto();
		    // Get largest photo's file_id
		    String f_id = photos.stream()
		            .max(Comparator.comparing(PhotoSize::getFileSize))
		            .orElseThrow().getFileId();
		    // Send photo by file_id we got before
		    String chatId = update.getMessage().getChatId().toString();
		    SendPhoto msg = new SendPhoto();
		    msg.setChatId(chatId); msg.setPhoto(new InputFile(f_id));
		    msg.setCaption("Photo");
		    try {
		        execute(msg); // Call method to send the photo
		    } catch (TelegramApiException e) {
		        e.printStackTrace();
		    }
		}
	}
    // Check that the update contains a message and the message has a photo
    
	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "@StringArtBotRu";
	}

	@Override
	public String getBotToken() {
		return "********";
	}
	
	
}
