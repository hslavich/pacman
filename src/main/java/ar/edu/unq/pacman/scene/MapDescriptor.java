package ar.edu.unq.pacman.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.appearances.Sprite;
import ar.edu.unq.americana.components.Background;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.component.Dot;

public class MapDescriptor {
	private BufferedImage path;
	private BufferedImage background;
	private static final String MAPS_PATH = "maps";
	private List<Dot> dots;
	
	public MapDescriptor(String levelName) throws IOException {
		super();
		this.dots = new ArrayList<Dot>();
		this.path = this.readImage("maps", levelName);
		this.createDots();
		this.generateBackground();
	}

	private void createDots() {
		int width = this.path.getWidth();
		int height = this.path.getHeight();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j ++) {
				final int pixel = this.path.getRGB(i, j);
				if (Color.red.getRGB() == pixel) {
					dots.add(new Dot(i, j));
				}
			}
		}
	}

	private void generateBackground() {
		this.background = new BufferedImage(this.path.getWidth(), this.path.getHeight(), this.path.getType());
		int width = this.path.getWidth();
		int height = this.path.getHeight();
		
		Graphics2D graphics = this.background.createGraphics();
		graphics.setPaint(Color.black);
		graphics.fillRect(0, 0, width, height);
	}
	
	private BufferedImage readImage(String basePath, String file) throws IOException {
		return ImageIO.read(ClassLoader.getSystemResource(MAPS_PATH + "/" + file + ".png"));
	}
	
	public Vector2D getPacmanInitialPos() {
		return new Vector2D(250, 330);
	}
	
	public Boolean isAccessible(int x, int y) {
		int pixel = this.path.getRGB(x, y);
		return ((pixel >> 24) & 0xff) != 0;
	}

	public List<Dot> getDots() {
		return dots;
	}

	public Background<GameScene> getBackgroundImage() {
		return new Background<GameScene>(new Sprite(this.background));
	}

}
