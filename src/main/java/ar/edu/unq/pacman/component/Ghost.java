package ar.edu.unq.pacman.component;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.pacman.event.InverseModeFinishEvent;
import ar.edu.unq.pacman.event.InverseModeStartEvent;
import ar.edu.unq.pacman.scene.GameMap;

public class Ghost extends Actor {

	@Property("ghost.size")
	protected static int SIZE;

	@Property("ghost.speed")
	private static int SPEED;

	protected Node prev;

	public Ghost(int row, int column) {
		super(row, column);
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

	@Events.Fired(InverseModeFinishEvent.class)
	protected void inverseModeFinish(InverseModeFinishEvent event) {
		this.setDefaultAppearance();
	}

	@Events.Update
	public void update(final double delta) {
		this.chooseNextDirection();
		this.move(delta);
	}

	public void die() {
		this.reset();
	}

	public void reset() {
		this.resetPosition();
		this.resetDirection();
		this.center();
	}

	@Override
	protected void fixCell() {
		this.prev = new Node(this.getRow(), this.getColumn(), 0);
		super.fixCell();
	}

	protected void chooseNextDirection() {
		if (this.offset == 0) {
			List<Node> adjs = this.getScene().adjacents(new Node(this.getRow(), this.getColumn(), 0));
			for (Iterator<Node> it = adjs.iterator(); it.hasNext();) {
				Node n = it.next();
				if (this.prev != null && this.prev.row() == n.row() && this.prev.column() == n.column()) {
					it.remove();
				}
			}
			Node node = adjs.get(0 + (int) (Math.random() * adjs.size()));
			this.setNextDirection(node.column() - this.getColumn(), node.row() - this.getRow());
		}
	}

	@Override
	protected double getSpeed() {
		return SPEED;
	}

}
