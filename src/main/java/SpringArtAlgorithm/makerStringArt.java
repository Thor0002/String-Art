package SpringArtAlgorithm;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.Color;

public class MakerStringArt {
	private int quality  = 1;
	
	public MakerStringArt() {}
	public MakerStringArt(int new_quality) {quality = new_quality;}
	
	public void setQuality(int new_quality) {quality = new_quality;}
    
	// Формирует String Art из изображения
	public  File getStringArtImage(File file) {
		int numPins = 256;
		int numLines = 1360 * quality; // 3 Варианта: (400, 1360), (800, 2720), (1200, 4080) 
		int center = 400 * quality;   //               низкое,      среднее,     высокое     качество
		int radius = 400 * quality;
		File output = new File("C:\\Users\\Кирилл\\Desktop\\Java\\Spring Art Telegram Bot\\New Image.jpg");
		try {
			BufferedImage image = ImageIO.read(file);
			BufferedImage result = new BufferedImage(2*radius + 1, 2*radius + 1, image.getType());


			int[] filter = {1, 2, 1, 2, 4, 2, 1, 2, 1};
			int filterWidth = 3;
			image = ModifierOfImage.blur(image, filter, filterWidth);

			BufferedImage greyImage = ModifierOfImage.imageToGrayScaleAndInvert(image); 

			BufferedImage imgCropped = ModifierOfImage.cropImage(greyImage);

			imgCropped = ModifierOfImage.resizeImage(imgCropped, 2*radius + 1, 2*radius + 1);

			int[][] modifedImage = new int[2*radius + 1][2*radius + 1];
			for(int x = 0; x < 2*radius; x++) {
				for(int y = 0; y < 2*radius; y++) {
					if((x - center)*(x - center) + (y - center)*(y - center) > radius * radius) {
						modifedImage[x][y] = 255;
					} else {
						modifedImage[x][y] = new Color(imgCropped.getRGB(x, y)).getBlue();
					}
				}
			}

			int[] pinX = new int[numPins];
			int[] pinY = new int[numPins];

			for(int i = 0; i < numPins/2; i++) {
				int x = (int)Math.round(center - radius * Math.cos(2 * Math.PI * i/numPins));
				pinX[i] = x;
				int y = (int)Math.round(center +  radius * Math.sin(2 * Math.PI * i/numPins));
				pinY[i] = y;
			}
			for(int i = numPins/2; i < numPins; i++) {
				int x = (int)Math.round(center - radius * Math.cos(2 * Math.PI * i/numPins));
				pinX[i] = x;
				int y = (int)Math.round(center + radius * Math.sin(2 * Math.PI * i/numPins));
				pinY[i] = y;
			}

			for(int x = 0; x <= 2*radius; x++) {
				for(int y = 0; y <= 2*radius; y++) {
					result.setRGB(x, y, new Color(255, 255, 255).getRGB());
				}
			}

			int oldPin = 0;
			int bestPin = 0;
			int[][] prevLines = new int[numPins][numPins]; 
			for(int i = 0; i < numPins; i++) {             
				for(int j = 0; j < numPins; j++) prevLines[i][j] = 0;
			}

			for(int line = 0; line < numLines; line++) {
				int bestLine = 0;
				int oldX = pinX[oldPin];
				int oldY = pinY[oldPin];
				for(int j = 1; j < numPins; j++) {
					int pin = (oldPin + j) % numPins;
					if(prevLines[oldPin][pin] == 1) {continue;}
					int x = pinX[pin];
					int y = pinY[pin];

					Pair<ArrayList<Integer>> pinLine = linePixels(oldX, oldY, x, y);

					int lineSum = 0;
					for(int i = 0; i < pinLine.x.size(); i++) {
						lineSum += modifedImage[pinLine.x.get(i)][pinLine.y.get(i)];
					}
					if(lineSum > bestLine) {
						bestLine = lineSum;
						bestPin = pin;
					}
				}
				if(oldPin == bestPin) break;

				Pair<ArrayList<Integer>> pinLine = linePixels(oldX, oldY, pinX[bestPin], pinY[bestPin]);
				for(int i = 0; i < pinLine.x.size(); i++) {
					result.setRGB(pinLine.x.get(i), pinLine.y.get(i), new Color(45, 45, 45).getRGB());
					modifedImage[pinLine.x.get(i)][pinLine.y.get(i)] -= 100;
				}
				prevLines[oldPin][bestPin] = 1;
				oldPin = bestPin;
			}

			ImageIO.write(result, "jpg", output);
		} catch (IOException e) {
			System.out.println("Ошибка что-то произошло не так");
		}
		
		return output;
	}

	// Формирует маасив пикселей на прямой между (x0, y0) и (x1, y1): алгоритм Брезенхема
	public static Pair<ArrayList<Integer>> linePixels(int x0, int y0, int x1, int y1){
		boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
		if(steep) {
			int t = x0;
			x0 = y0;
			y0 = t;

			t = x1;
			x1 = y1;
			y1 = t;
		}

		if(x0 > x1) {
			int t = x0;
			x0 = x1;
			x1 = t;

			t = y0;
			y0 = y1;
			y1 = t;
		}

		int dx = x1 - x0;
		int dy = Math.abs(y1 - y0);
		int error = dx / 2;
		int stepy = (y0 < y1) ? 1 : -1;
		int y = y0;
		ArrayList<Integer> xList = new ArrayList<Integer>();
		ArrayList<Integer> yList = new ArrayList<Integer>();
		for(int x = x0; x <= x1; x++) {
			int nx = steep ? y : x;
			int ny = steep ? x : y;
			xList.add(nx);
			yList.add(ny);
			error -= dy;
			if(error < 0) {
				y += stepy;
				error += dx;
			}
		}

		return new Pair<ArrayList<Integer>>(xList, yList);
	}

}
