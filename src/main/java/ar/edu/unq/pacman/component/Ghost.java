package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.pacman.event.InverseModeStartEvent;
import ar.edu.unq.pacman.scene.GameMap;

public class Ghost extends PositionableComponent<GameMap> {
	
	@Property("ghost.size")
	protected static int SIZE;
	
	public Ghost(int row, int column) {
		this.resetPosition(row, column);
		this.resetPosition();
		this.setDefaultAppearance();
	}
	
	private void setDefaultAppearance() {
		this.setAppearance(new Circle(Color.RED, SIZE));
	}

	@Override
	public void onSceneActivated() {
		super.onSceneActivated();
		this.setX(this.getColumn() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
		this.setY(this.getRow() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
	}
	
	@Events.Fired(InverseModeStartEvent.class)
	protected void inverseModeStart(InverseModeStartEvent event) {
		this.setAppearance(new Circle(Color.BLUE, SIZE));
	}
}
