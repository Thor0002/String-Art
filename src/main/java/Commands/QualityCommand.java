package Commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import SpringArtAlgorithm.MakerStringArt;


public class QualityCommand extends ServiceCommand {
	private MakerStringArt modifier;

    public QualityCommand(String identifier, String description,MakerStringArt new_modifier) {
        super(identifier, description);
        modifier = new_modifier;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        
        if (strings.length == 0) {
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        			"Это команда позволяет указать качество(точность) для формирования изображения.\n" +
        	        "Сейчас доступны варианты: 1, 2, 3. Чем больше, тем точнее. Но займёт больше времени.\n" + 
        			"Чтобы выбрать нужно ввести команду\n/quality и номер качетсва \n" +
        	        "Например: \"/quality 2\"");
        } else if (strings.length == 1) {
        	InputStream str = new ByteArrayInputStream(strings[0].getBytes());
        	Scanner sc = new Scanner(str);
        	int a = 0;
        	try {
    			a = sc.nextInt();//Считываем второе число
    		} catch(Exception e) {
    			sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
            			"Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /quality\n");
    			sc.close();
    			return;
    		}
        	if (a == 1 || a == 2 || a ==3) {
        		modifier.setQuality(a);
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
            			"Установлено качество " + a + "\n");
        	} else {
        		sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
            			"Похоже что сейчас такое качество не доступно. Чтобы посмотреть какие есть введите /quality\n");
        	}
        	sc.close();
        	
        } else {
        	sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
        			"Похоже вы ввели соообщение неверного формата. Чтобы посмотреть правильный формат введите /quality\n");
        }

    }
}
