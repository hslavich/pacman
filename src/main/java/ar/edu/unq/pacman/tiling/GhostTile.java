package ar.edu.unq.pacman.tiling;

import java.awt.Color;

import ar.edu.unq.pacman.scene.GameMap;

public class GhostTile implements ITile {

	protected static String[] NAMES = new String[] { "red", "pink", "green", "orange" };

	protected static int GHOSTS = 4;

	@Override
	public void apply(GameMap map, int pixel, int row, int column) {
		if (Color.blue.getRGB() == pixel) {
			for (int i = 0; i < GHOSTS; i++) {
				map.addGhost(row, column, NAMES[i % NAMES.length]);
			}
		}
	}
}
