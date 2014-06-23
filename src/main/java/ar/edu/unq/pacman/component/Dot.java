package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.pacman.scene.GameMap;
import ar.edu.unq.americana.appearances.Rectangle;

public class Dot extends GameComponent<GameMap> {
	
	@Property("dot.size")
	private static int SIZE;
	
	public Dot(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setAppearance(new Rectangle(Color.orange, SIZE, SIZE));
	}
	
	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = Pacman.class)
	private void checkPacmanCollision(final Pacman pacman) {
		this.getScene().destroyDot(this);
	}
}
