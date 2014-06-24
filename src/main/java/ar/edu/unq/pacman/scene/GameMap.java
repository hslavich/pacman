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
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.component.Dot;
import ar.edu.unq.pacman.component.Pacman;

public class GameMap extends GameScene implements ITileMapScene {
	
	private Pacman pacman;
	
	private MapDescriptor mapDescriptor;
	
	private String levelName;
	
	private BaseTileMap tileMap;
	
	private int rows;
	
	private int columns;

	private ITileMapResourceProvider tileMapResourceProvider;
	
	@Property("cell.width")
	public static int CELL_WIDTH;

	@Property("cell.height")
	public static int CELL_HEIGHT;
	
	public GameMap(String levelName) throws IOException {
		super();
		this.levelName = levelName;
		this.mapDescriptor = new MapDescriptor(this.levelName);
		this.setBackground();
		initializeTileMap();
	}
	
	private void initializeTileMap() {
		this.tileMapResourceProvider = new BaseTileMapResourceProvider(this.rows, this.columns);
		this.tileMapResourceProvider.register(1, SpriteResources.sprite("assets/tiles", "wall"));
		this.tileMapResourceProvider.register(2, SpriteResources.sprite("assets/tiles", "path"));
		
		this.tileMap = new BaseTileMap(this, CELL_WIDTH, CELL_HEIGHT, this.tileMapResourceProvider);
	}

	private void setBackground() {
		this.addComponent(this.mapDescriptor.getBackgroundImage());
	}

	@Override
	protected void addGameComponents() {
		super.addGameComponents();
		
//		this.pacman = new Pacman();
		this.addComponent(this.pacman);
		
		this.addDots();
	}
	
	protected void addDots() {
		this.addComponents(this.mapDescriptor.getDots());
	}

	public Pacman getPacman() {
		return pacman;
	}

	public void destroyDot(Dot dot) {
		dot.destroy();
	}
	
	public Boolean isAccessible(Vector2D pos) {
		return this.mapDescriptor.isAccessible(Double.valueOf(pos.getX()).intValue(), Double.valueOf(pos.getY()).intValue());
	}
	
	public void addWall(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 1);
	}
	
	public void addPath(final int row, final int column) {
		this.tileMapResourceProvider.putAt(row, column, 2);
	}

	@Override
	public int columnsCount() {
		return 0;
	}

	@Override
	public int rowsCount() {
		return 0;
	}

	@Override
	public boolean isAccessible(int row, int column) {
		return false;
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