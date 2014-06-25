package ar.edu.unq.pacman.tiling;

import ar.edu.unq.pacman.scene.GameMap;

public interface ITile {
	
	public void apply(GameMap map, int pixel, int row, int column);
}
