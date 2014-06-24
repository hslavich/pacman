package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.PacmanGame;
import ar.edu.unq.pacman.scene.GameMap;

public class Pacman extends PositionableComponent<GameMap>{
	
	@Property("pacman.diameter")
	private static int DIAMETER;
	
	@Property("pacman.speed")
	private static int SPEED;
	
	private double vOffset;
	
	private double hOffset;
	
	public Pacman(int row, int column) {
		this.setAppearance(new Circle(Color.yellow, DIAMETER));
		this.hOffset = 0;
		this.vOffset = 0;
		this.setRow(row);
		this.setColumn(column);
	}
	
	@Override
	public void onSceneActivated() {
		super.onSceneActivated();
		this.setX(this.getColumn() * GameMap.CELL_WIDTH);
		this.setY(this.getRow() * GameMap.CELL_HEIGHT);
	}
	
	@Events.Keyboard(type = EventType.BeingHold, key = Key.D)
	private void goRight(final DeltaState state) {
		this.setDirection(1, 0);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.A)
	private void goLeft(final DeltaState state) {
		this.setDirection(-1, 0);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.S)
	private void goDown(final DeltaState state) {
		this.setDirection(0, 1);
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.W)
	private void goUp(final DeltaState state) {
		this.setDirection(0, -1);
	}
	
	@Events.Update
	public void update(final double delta) {
		Vector2D where = this.getDirection().multiply(delta * SPEED);
		Vector2D newPos = new Vector2D(this.getX(), this.getY()).suma(where);
		if (this.getScene().isAccessible(newPos)) {
			this.setX(newPos.getX());
			this.setY(newPos.getY());
		}
	}
}
