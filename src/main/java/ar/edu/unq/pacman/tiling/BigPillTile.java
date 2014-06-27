package ar.edu.unq.pacman.tiling;

import java.awt.Color;

import ar.edu.unq.pacman.component.BigPill;
import ar.edu.unq.pacman.scene.GameMap;

public class BigPillTile implements ITile {

	@Override
	public void apply(GameMap map, int pixel, int row, int column) {
		if (pixel == Color.GREEN.getRGB()) {
			map.addPill(new BigPill(row, column));
		}
	}

}
