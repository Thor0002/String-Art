package SpringArtAlgorithm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.IntStream;

public class ModifierOfImage {
	// Method to blur image
		public static BufferedImage blur(BufferedImage originalImage, int[] filter, int filterWidth) {
		    if (filter.length % filterWidth != 0) {
		        throw new IllegalArgumentException("filter contains a incomplete row");
		    }

		    final int width = originalImage.getWidth();
		    final int height = originalImage.getHeight();
		    final int sum = IntStream.of(filter).sum();

		    int[] input = originalImage.getRGB(0, 0, width, height, null, 0, width);

		    int[] output = new int[input.length];

		    final int pixelIndexOffset = width - filterWidth;
		    final int centerOffsetX = filterWidth / 2;
		    final int centerOffsetY = filter.length / filterWidth / 2;

		    // apply filter
		    for (int h = height - filter.length / filterWidth + 1, w = width - filterWidth + 1, y = 0; y < h; y++) {
		        for (int x = 0; x < w; x++) {
		            int r = 0;
		            int g = 0;
		            int b = 0;
		            for (int filterIndex = 0, pixelIndex = y * width + x;
		                    filterIndex < filter.length;
		                    pixelIndex += pixelIndexOffset) {
		                for (int fx = 0; fx < filterWidth; fx++, pixelIndex++, filterIndex++) {
		                    int col = input[pixelIndex];
		                    int factor = filter[filterIndex];

		                    // sum up color channels separately
		                    r += ((col >>> 16) & 0xFF) * factor;
		                    g += ((col >>> 8) & 0xFF) * factor;
		                    b += (col & 0xFF) * factor;
		                }
		            }
		            r /= sum;
		            g /= sum;
		            b /= sum;
		            // combine channels with full opacity
		            output[x + centerOffsetX + (y + centerOffsetY) * width] = (r << 16) | (g << 8) | b | 0xFF000000;
		        }
		    }

		    BufferedImage result = new BufferedImage(width, height, originalImage.getType());
		    result.setRGB(0, 0, width, height, output, 0, width);
		    return result;
		}
		
		// Method to resize image
		public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
			BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
			Graphics2D graphics2D = resizedImage.createGraphics();
			graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
			graphics2D.dispose();
			return resizedImage;
		}
		
		// Method to make an image black and white and invert
		public static BufferedImage imageToGrayScaleAndInvert(BufferedImage originalImage) {
			BufferedImage greyImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
			for(int x = 0; x < originalImage.getWidth(); x++) {
				for(int y = 0; y < originalImage.getHeight(); y++) {
					Color color = new Color(originalImage.getRGB(x, y));
					int blue = color.getBlue();
					int red = color.getRed();
					int green = color.getGreen();
					int grey = 255 - (int) (red * 0.299 + green * 0.587 + blue * 0.114);
					Color newColor = new Color(grey, grey, grey);
					greyImage.setRGB(x, y, newColor.getRGB());
				}
			}
			return greyImage;
		}
		
		public static BufferedImage cropImage(BufferedImage originalImage) {
			int height = originalImage.getHeight();
			int width = originalImage.getWidth();
			int minEdge = Math.min(height, width);
			int leftEdge = (int)(width - minEdge)/2;
			int topEdge = (int)(height - minEdge)/2;
			BufferedImage imgCropped = new BufferedImage(minEdge, minEdge, originalImage.getType());
			for(int x = 0; x < minEdge; x++) {
				for(int y = 0; y < minEdge; y++) {
					Color color = new Color(originalImage.getRGB(leftEdge + x,topEdge + y));
					imgCropped.setRGB(x, y, color.getRGB());
				}
			}
			return imgCropped;
		}

}
