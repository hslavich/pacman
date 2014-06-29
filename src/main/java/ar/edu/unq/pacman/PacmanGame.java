package ar.edu.unq.pacman;

import java.awt.Dimension;
import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.utils.ResourcesUtils;
import ar.edu.unq.pacman.tiling.MapProvider;

public class PacmanGame extends Game {

	@Property("game.width")
	private static int WIDTH;

	@Property("game.height")
	private static int HEIGHT;

	private Dimension dimension;

	private GameStatus gameStatus;

	public static final Font font = ResourcesUtils.getFont("assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT,
			Font.BOLD, 50);

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

	@Override
	public void startGame() {
		this.gameStatus = new GameStatus();
		this.setCurrentScene(MapProvider.getMap("level1"));
	}

	public void nextLevel() {
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

	public GameStatus getGameStatus() {
		return this.gameStatus;
	}
}
