package ar.edu.unq.pacman;

import java.awt.Color;

import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.LifeCounter;
import ar.edu.unq.americana.components.Score;
import ar.edu.unq.pacman.scene.GameMap;

public class GameStatus {

	private Score<GameMap> score;

	private LifeCounter<GameMap> lifeCounter;

	public GameStatus() {
		this.score = new Score<GameMap>(10, PacmanGame.font, Color.GRAY);
		this.lifeCounter = new LifeCounter<GameMap>(3, SpriteResources.sprite("assets/pacman/pacman", "pacman-right1"));
	}

	public Score<GameMap> getScore() {
		return this.score;
	}

	public void setScore(Score<GameMap> score) {
		this.score = score;
	}

	public LifeCounter<GameMap> getLifeCounter() {
		return this.lifeCounter;
	}

	public void setLifeCounter(LifeCounter<GameMap> lifeCounter) {
		this.lifeCounter = lifeCounter;
	}

}
