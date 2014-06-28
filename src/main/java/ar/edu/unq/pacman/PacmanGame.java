package ar.edu.unq.pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.utils.ResourcesUtils;
import ar.edu.unq.pacman.scene.GameMap;
import ar.edu.unq.pacman.tiling.MapProvider;

public class PacmanGame extends Game {

	@Property("game.width")
	private static int WIDTH;
	
	@Property("game.height")
	private static int HEIGHT;

	private Dimension dimension;
	
	@Override
	protected String[] properties() {
		return new String[] { "pacman.properties" };
	}

	@Override
	protected void initializeResources() {
	}

	@Override
	protected void setUpScenes() {
		this.startGame();
	}

	public void startGame() {
		this.setCurrentScene(MapProvider.getMap("level1"));
	}

	@Override
	public Dimension getDisplaySize() {
		if (this.dimension == null) {
			this.dimension = new Dimension(WIDTH, HEIGHT);
		}
		return this.dimension;
	}

	@Override
	public String getTitle() {
		return "Pacman";
	}
}
