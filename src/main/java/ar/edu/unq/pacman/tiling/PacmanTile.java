package ar.edu.unq.pacman.tiling;

import java.awt.Color;

import ar.edu.unq.pacman.scene.GameMap;

public class PacmanTile implements ITile {

	@Override
	public void apply(GameMap map, int pixel, int row, int column) {
		if (Color.white.getRGB() == pixel) {
			map.setPacmanInitialPos(row, column);
		}
	}
}
