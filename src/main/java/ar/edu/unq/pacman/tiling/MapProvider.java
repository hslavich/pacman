package ar.edu.unq.pacman.tiling;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import ar.edu.unq.pacman.scene.GameMap;

public class MapProvider {
	
	public static GameMap getMap(String levelName) {
		GameMap map = null;
		try {
			BufferedImage img = ImageIO.read(ClassLoader.getSystemResource("maps/" + levelName + ".png"));
			int width = img.getWidth();
			int height = img.getHeight();
			
			System.out.println(width);
			System.out.println(height);
			
			map = new GameMap(height, width);
			
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j ++) {
					final int pixel = img.getRGB(i, j);
					new TileProvider().apply(map, pixel, j, i);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
