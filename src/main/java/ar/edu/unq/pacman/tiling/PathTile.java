package ar.edu.unq.pacman.tiling;

import java.awt.Color;

import ar.edu.unq.pacman.scene.GameMap;

public class PathTile implements ITile {

	@Override
	public void apply(GameMap map, int pixel, int row, int column) {
		if (pixel != Color.BLACK.getRGB()) {
			map.addPath(row, column);
		}
	}

}
