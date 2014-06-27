package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.pacman.scene.GameMap;
import ar.edu.unq.pacman.scene.PacmanGameOverScene;
import ar.edu.unq.americana.appearances.Rectangle;

public class Pill extends GameComponent<GameMap> {
	
	@Property("dot.size")
	private static int SIZE;
	
	public Pill(int row, int column) {
		this.setX(column * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
		this.setY(row * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
		this.setAppearance(new Rectangle(Color.orange, SIZE, SIZE));
	}
	
	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = Pacman.class)
	private void checkPacmanCollision(final Pacman pacman) {
		this.getScene().destroyPill(this);
		if(this.getScene().getPills().size() == 0){
//			this.getGame().closeGame();
			this.getGame().setCurrentScene(new PacmanGameOverScene());
		}
	}
}
