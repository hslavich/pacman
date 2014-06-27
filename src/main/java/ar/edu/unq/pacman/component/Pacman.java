package ar.edu.unq.pacman.component;

import java.awt.Font;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.americana.scenes.components.tilemap.PositionableComponent;
import ar.edu.unq.americana.utils.ResourcesUtils;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.event.InverseModeFinishEvent;
import ar.edu.unq.pacman.event.InverseModeStartEvent;
import ar.edu.unq.pacman.scene.GameMap;

public class Pacman extends PositionableComponent<GameMap> {

	@Property("pacman.diameter")
	private static int DIAMETER;

	@Property("pacman.speed")
	private static int SPEED;

	private double offset;

	private Vector2D nextDirection;

	private Vector2D dir;

	private boolean inverseMode = false;

	public static final Font font = ResourcesUtils.getFont("assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT,
			Font.BOLD, 50);

	public Pacman(int row, int column) {
		this.setAppearance(SpriteResources.sprite("assets/pacman/pacman", "pacman-full"));
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
		if (this.inverseMode) {
			this.getScene().ghostDie(ghost);
		} else {
			this.getScene().pacmanDie();
		}
	}

	@Events.Fired(InverseModeStartEvent.class)
	protected void inverseModeStart(InverseModeStartEvent event) {
		this.inverseMode = true;
	}

	@Events.Fired(InverseModeFinishEvent.class)
	protected void inverseModeFinish(InverseModeFinishEvent event) {
		this.inverseMode = false;
	}

	public void setDir(double x, double y) {
		if (x == 1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-right"));
		} else if (y == 1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-down"));
		} else if (x == -1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-left"));
		} else if (y == -1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-up"));

		}
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

		if (this.getColumn() > this.getScene().columnsCount()) {
			this.setColumn(-1);
		} else if (this.getColumn() < -1) {
			this.setColumn(this.getScene().columnsCount());
		}
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
