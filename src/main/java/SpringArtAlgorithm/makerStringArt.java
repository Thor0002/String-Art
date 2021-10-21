package SpringArtAlgorithm;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.awt.Color;

public class makerStringArt {

	//	public static void main(String[] args) throws IOException {
	public static BufferedImage getStringArtImage(BufferedImage image) {
		int numPins = 256;
		int numLines = 2000;
		int center = 500;
		int radius = 500;
		BufferedImage result = new BufferedImage(2*radius + 1, 2*radius + 1, image.getType());
		
		try {
//			File file = new File("Image3.jpg");
//			BufferedImage image = ImageIO.read(file);



			int[] filter = {1, 2, 1, 2, 4, 2, 1, 2, 1};
			int filterWidth = 3;
			image = ModifierOfImage.blur(image, filter, filterWidth);

			BufferedImage greyImage = ModifierOfImage.imageToGrayScaleAndInvert(image); 

			BufferedImage imgCropped = ModifierOfImage.cropImage(greyImage);

			imgCropped = ModifierOfImage.resizeImage(imgCropped, 1001, 1001);

			// save pixels from masked Image to Array
			int[][] modifedImage = new int[1001][1001];
			for(int x = 0; x < 1000; x++) {
				for(int y = 0; y < 1000; y++) {
					if((x - 500)*(x - 500) + (y - 500)*(y - 500) > 500 * 500) {
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
			int[][] prevLines = new int[numPins][numPins]; // so as not to draw the line again
			for(int i = 0; i < numPins; i++) {             // initialization
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


//			File output = new File("New Image.jpg");
//			ImageIO.write(result, "jpg", output);
		} catch (IOException e) {
			System.out.println("Ошибка что-то произошло не так");
		}
		
		return result;
	}

	// Method to find Pixels on line betwen (x0, y0) and (x1, y1) : Bresenham's algorithm
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
