package DataBase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class UserService {
	private static UserDAO userDAO = new UserDAO();

	// Регистрация профиля
	public static String Registration(String userName, String userPassword) {
		List<User> Users = userDAO.findByName(userName);
		if(Users.size() == 1){
			return "Вы уже зарегестрированы.\n";
		} else {
			User user = new User(userName, userPassword);
			int t = 0;
			try {
				userDAO.saveUser(user);
				t = 1;
			} catch (Exception e) {
				t = 0;
			}
			if (t == 0) {return "Не удалось зарегестрироваться, Попробуйте позже.\n";}
			else {return "Вы зарегестрированы успешно. Ваш пароль:" + userPassword + "\n";}
		}		
	}
	
	// Войти в профиль
	// false - Не удалось войти
	// true - Войти удалось
	public static boolean Login(String userName, String userPassword) {
		List<User> Users = userDAO.findByName(userName);
		if(Users.size() == 0){return false;}
		else if (Users.get(0).getUserPassword().equals(userPassword)) {return true;}
		else {return false;}
	}
    
	// Сохранить изображение в базу данных
	public static String Save(String userName, String imageName)  {
		UserDAO userDAO = new UserDAO();
		List<User> Users = userDAO.findByName(userName);
		if(Users.size() == 0){
			return "Вы не зарегестрированы.\n";
		} else {
			User user = Users.get(0);
			java.io.File NewImage  = new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Session\\" + userName+ ".jpg");

			java.io.File theDir =new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Images\\"+userName);
			if (!theDir.exists())
			{
				theDir.mkdir();
			}

			java.io.File target=new java.io.File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\Images\\"+userName+"\\"+imageName+".jpg");
			try {
				BufferedImage image = ImageIO.read(NewImage);
				ImageIO.write(image, "jpg", target);
			} catch (IOException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Image image = new Image();
			image.setImageName(imageName);
			image.setUser(user);
			userDAO.saveImage(image);

			return "Файл сохранен\n";
		}
	}
	
	// Загрузить изображение из базы данных
	// false - Не удалось загрузить.
	// true - Удалось загрузить
    public static boolean Upload(String userName, String imageName)  {
        UserDAO userDAO = new UserDAO();
        List<Image> Images = userDAO.findByImageNameAndUser(userName, imageName);
        if(Images.size() == 0){ return false;}
        else {return true;}
    }

}
