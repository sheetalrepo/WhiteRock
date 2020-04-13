package com;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ArtWork {

	public static void artWork1(String displayName, int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.drawString(displayName, 12, 24);

		for (int y = 0; y < height; y++) {
			StringBuilder stringBuilder = new StringBuilder();
			for (int x = 0; x < width; x++) {
				stringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? "$" : " ");
			}
			if (stringBuilder.toString().trim().isEmpty()) {
				continue;
			}
			System.out.println(stringBuilder);
		}
	}

	public static void artWork2(String displayMessage, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.BOLD, 24));

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(displayMessage, 10, 20);

		for (int y = 0; y < height; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < width; x++) {
				sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
			}
			if (sb.toString().trim().isEmpty()) {
				continue;
			}
			System.out.println(sb);
		}

	}
}
