package ar.edu.unq.pacman.component;

import ar.edu.unq.americana.DeltaState;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.constants.Key;
import ar.edu.unq.americana.events.annotations.EventType;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.events.ioc.collision.CollisionStrategy;
import ar.edu.unq.pacman.event.InverseModeFinishEvent;
import ar.edu.unq.pacman.event.InverseModeStartEvent;
import ar.edu.unq.pacman.scene.GameMap;

public class Pacman extends Actor {

	@Property("pacman.diameter")
	private static int DIAMETER;

	@Property("pacman.speed")
	private static int SPEED;

	private boolean inverseMode = false;

	private boolean dying = false;

	public Pacman(int row, int column) {
		super(row, column);
		this.setDefaultAppearence();
	}

	protected void setDefaultAppearence() {
		this.setAppearance(SpriteResources.sprite("assets/pacman/pacman", "pacman-full"));
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
		if (!this.lock) {
			if (ghost.inverseMode) {
				this.getScene().ghostDie(ghost);
			} else {
				this.dying = true;
				this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-die"));
				this.getScene().lock();
			}
		}
	}

	@Override
	public void onAnimationEnd() {
		if (this.dying) {
			this.getScene().pacmanDie();
			this.dying = false;
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

	@Override
	public void setDir(double x, double y) {
		if (this.getDir().getX() == x && this.getDir().getY() == y) {
			return;
		}

		if (x == 1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-right"));
		} else if (y == 1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-down"));
		} else if (x == -1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-left"));
		} else if (y == -1) {
			this.setAppearance(SpriteResources.animation("assets/pacman/pacman", "pacman-up"));
		}
		super.setDir(x, y);
	}

	@Events.Update
	public void update(final double delta) {
		this.move(delta);
	}

	public void reset() {
		this.setDefaultAppearence();
		this.resetPosition();
		this.resetDirection();
		this.center();
	}

	@Override
	protected double getSpeed() {
		double delta = 1;
		if (this.inverseMode) {
			delta = 1.3;
		}
		return SPEED * delta;
	}
}
