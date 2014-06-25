package ar.edu.unq.pacman.scene;

import java.io.IOException;
import java.util.List;

import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.americana.ia.pathfindier.TileMap;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMap;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapScene;
import ar.edu.unq.americana.scenes.components.tilemap.TileMapBackground;
import ar.edu.unq.pacman.component.Dot;
import ar.edu.unq.pacman.component.Pacman;

public class GameMap extends GameScene implements ITileMapScene {
	
	private Pacman pacman;
	
	private BaseTileMap tileMap;
	
	private int rows;
	
	private int columns;

	private ITileMapResourceProvider tileMapResourceProvider;
	
	private boolean[][] path;

	private int pacmanInitRow;
	
	private int pacmanInitColumn;
	
	@Property("cell.size")
	public static int CELL_SIZE;
	
	public GameMap(int rows, int columns) throws IOException {
		super();
		this.path = new boolean[rows][columns];
		this.rows = rows;
		this.columns = columns;
		initializeTileMap();
	}
	
	private void initializeTileMap() {
		this.tileMapResourceProvider = new BaseTileMapResourceProvider(this.rows, this.columns);
		this.tileMapResourceProvider.register(1, SpriteResources.sprite("assets/wall", "wall"));
		this.tileMapResourceProvider.register(2, SpriteResources.sprite("assets/wall", "path"));
		
		this.tileMap = new BaseTileMap(this, CELL_SIZE, CELL_SIZE, this.tileMapResourceProvider);
	}

	@Override
	protected void addGameComponents() {
		super.addGameComponents();
		
		this.pacman = new Pacman(this.pacmanInitRow, this.pacmanInitColumn);
		this.addComponent(this.pacman);
		
//		this.addDots();
	}
	
	public void setPacmanInitialPos(int row, int column) {
		this.pacmanInitRow = row;
		this.pacmanInitColumn = column;
	}
	
	public Pacman getPacman() {
		return pacman;
	}

	public void destroyDot(Dot dot) {
		dot.destroy();
	}
	
	public void addWall(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 1);
	}
	
	public void addPath(final int row, final int column) {
		this.path[row][column] = true;
		this.tileMapResourceProvider.putAt(row, column, 2);
	}

	@Override
	public int columnsCount() {
		return this.columns;
	}

	@Override
	public int rowsCount() {
		return this.rows;
	}

	@Override
	public boolean isAccessible(int row, int column) {
		return this.path[row][column];
	}

	@Override
	public List<Node> adjacents(Node node) {
		return null;
	}

	@Override
	public TileMap getTileMap() {
		return this.tileMap;
	}

	@Override
	public void addTileBackground(TileMapBackground tileMapBackGround) {
		this.addComponent(tileMapBackGround);
	}
}