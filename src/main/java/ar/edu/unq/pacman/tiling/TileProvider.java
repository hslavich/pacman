package ar.edu.unq.pacman.tiling;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.pacman.scene.GameMap;

public class TileProvider {
	
	private List<ITile> tiles;
	
	public TileProvider() {
		this.tiles = new ArrayList<ITile>();
		this.addTiles();
	}

	protected void addTiles() {
		this.tiles.add(new PathTile());
		this.tiles.add(new WallTile());
		this.tiles.add(new PillTile());
		this.tiles.add(new BigPillTile());
		this.tiles.add(new PacmanTile());
		this.tiles.add(new GhostTile());
	}
	
	public void apply(GameMap map, int pixel, int row, int column) {
		for (ITile tile : this.tiles) {
			tile.apply(map, pixel, row, column);
		}
	}
}
