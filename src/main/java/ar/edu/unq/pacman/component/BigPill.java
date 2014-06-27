package ar.edu.unq.pacman.component;

import java.awt.Color;

import ar.edu.unq.americana.appearances.Circle;

public class BigPill extends Pill {

	public BigPill(int row, int column) {
		super(row, column);
	}
	
	protected void setAppearance() {
		this.setAppearance(new Circle(Color.WHITE, SIZE * 4));
	}

}
