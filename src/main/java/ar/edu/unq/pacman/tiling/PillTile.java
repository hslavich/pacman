package ar.edu.unq.pacman.tiling;

import java.awt.Color;

import ar.edu.unq.pacman.component.Pill;
import ar.edu.unq.pacman.scene.GameMap;

public class PillTile implements ITile {

	@Override
	public void apply(GameMap map, int pixel, int row, int column) {
		if (pixel == Color.RED.getRGB()) {
			map.addPill(new Pill(row, column));
		}
	}

}
