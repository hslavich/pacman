package ar.edu.unq.pacman.component;

import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.scene.GameMap;

public abstract class Actor extends PositionableComponent<GameMap> {

	private Vector2D nextDirection;

	private Vector2D dir;

	private double offset;

	public Actor(int row, int column) {
		this.offset = 0;
		this.dir = new Vector2D(0, 0);
		this.nextDirection = new Vector2D(0, 0);
		this.setRow(row);
		this.setColumn(column);
		this.resetPosition(row, column);
	}

	public void setDir(double x, double y) {
		this.dir = new Vector2D(x, y);
	}

	public Vector2D getDir() {
		return this.dir;
	}

	protected void setNextDirection(int x, int y) {
		this.nextDirection = new Vector2D(x, y);
	}

	protected Vector2D getNextDirection() {
		return this.nextDirection;
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

	protected void center() {
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

		if (this.getColumn() > this.getScene().columnsCount()) {
			this.setColumn(-1);
		} else if (this.getColumn() < -1) {
			this.setColumn(this.getScene().columnsCount());
		}
	}

	protected void move(double delta) {
		double distance = this.getSpeed() * delta;
		this.updateDirection();
		this.advance(distance);
	}

	protected abstract double getSpeed();

	protected void advance(double distance) {
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

	protected void resetDirection() {
		this.setDir(0, 0);
		this.setNextDirection(0, 0);
		this.offset = 0;
	}
}
