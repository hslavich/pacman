package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.appearances.Circle;
import ar.edu.unq.pacman.event.InverseModeStartEvent;

public class BigPill extends Pill {

	public BigPill(int row, int column) {
		super(row, column);
	}
	
	protected void setAppearance() {
		this.setAppearance(new Circle(Color.WHITE, SIZE * 4));
	}
	
	@Override
	protected void checkPacmanCollision(Pacman pacman) {
		super.checkPacmanCollision(pacman);
		this.fire(new InverseModeStartEvent());
	}

}
