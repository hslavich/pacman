package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.scene.GameMap;

public class Pacman extends PositionableComponent<GameMap>{
	
	@Property("pacman.diameter")
	private static int DIAMETER;
	
	@Property("pacman.speed")
	private static int SPEED;
	
	private double offset;
	
	private Vector2D nextDirection;
	
	private Vector2D dir;
	
	public Pacman(int row, int column) {
		this.setAppearance(new Circle(Color.yellow, DIAMETER));
		this.offset = 0;
		this.dir = new Vector2D(0, 0);
		this.nextDirection = new Vector2D(0, 0);
		this.setRow(row);
		this.setColumn(column);
		this.resetPosition(row, column);
	}
	
	@Override
	public void onSceneActivated() {
		super.onSceneActivated();
		this.setX(this.getColumn() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
		this.setY(this.getRow() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2));
	}
	
	@Events.Keyboard(type = EventType.BeingHold, key = Key.D)
	private void goRight(final DeltaState state) {
		if (this.getScene().isAccessible(this.getRow(), this.getColumn() + 1)) {
			this.setNextDirection(1, 0);
		}
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.A)
	private void goLeft(final DeltaState state) {
		if (this.getScene().isAccessible(this.getRow(), this.getColumn() - 1)) {
			this.setNextDirection(-1, 0);
		}
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.S)
	private void goDown(final DeltaState state) {
		if (this.getScene().isAccessible(this.getRow() + 1, this.getColumn())) {
			this.setNextDirection(0, 1);
		}
	}

	@Events.Keyboard(type = EventType.BeingHold, key = Key.W)
	private void goUp(final DeltaState state) {
		if (this.getScene().isAccessible(this.getRow() - 1, this.getColumn())) {
			this.setNextDirection(0, -1);
		}
	}
	
	@Events.ColitionCheck.ForType(collisionStrategy = CollisionStrategy.FromBounds, type = Ghost.class)
	private void checkPacmanCollision(final Ghost ghost) {
		this.getScene().pacmanDie();
	}
	
	public void setDir(double x, double y) {
		this.dir = new Vector2D(x, y);
	}
	
	protected void setNextDirection(int x, int y) {
		this.nextDirection = new Vector2D(x, y);
	}
	
	@Events.Update
	public void update(final double delta) {
		double distance = SPEED * delta;
		this.updateDirection();
		this.move(distance);
	}

	protected void updateDirection() {
		if (this.offset == 0 && !this.nextDirection.equals(new Vector2D(0, 0))) {
			if (this.canMove()) {
				this.setDir(this.nextDirection.getX(), this.nextDirection.getY());
				this.fixCell();
			} else {
				this.setDir(0, 0);
			}
		}
	}
	
	private void center() {
		double dx = this.getColumn() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2);
		double dy = this.getRow() * GameMap.CELL_SIZE + (GameMap.CELL_SIZE / 2);
		
		this.setX(dx);
		this.setY(dy);
	}

	protected boolean canMove() {
		return this.getScene().isAccessible(this.getRow() + (int) this.nextDirection.getY(),
				this.getColumn() + (int) this.nextDirection.getX());
	}
	
	protected void fixCell() {
		this.fixColumn((int) this.dir.getX());
		this.fixRow((int) this.dir.getY());
	}
	
	protected void move(double distance) {
		if (!this.dir.equals(new Vector2D(0, 0))) {
			this.updateOffset(distance);
			if (this.offset != 0) {
				this.move(this.dir.multiply(distance));
			}
		}
	}
	
	protected void updateOffset(double distance) {
		this.offset += distance;
		if (this.offset >= GameMap.CELL_SIZE - 1) {
			this.offset = 0;
			this.center();
		}
	}

	public void reset() {
		this.resetPosition();
		this.resetDirection();
		this.center();
	}

	private void resetDirection() {
		this.setDir(0, 0);
		this.setNextDirection(0, 0);
		this.offset = 0;
	}
}
