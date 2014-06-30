package ar.edu.unq.pacman.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.appearances.utils.SpriteResources;
import ar.edu.unq.americana.components.Timer;
import ar.edu.unq.americana.components.events.ScoreUpEvent;
import ar.edu.unq.americana.configs.Property;
import ar.edu.unq.americana.events.annotations.Events;
import ar.edu.unq.americana.ia.pathfindier.Node;
import ar.edu.unq.americana.ia.pathfindier.TileMap;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMap;
import ar.edu.unq.americana.scenes.components.tilemap.BaseTileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapResourceProvider;
import ar.edu.unq.americana.scenes.components.tilemap.ITileMapScene;
import ar.edu.unq.americana.scenes.components.tilemap.TileMapBackground;
import ar.edu.unq.pacman.GameStatus;
import ar.edu.unq.pacman.PacmanGame;
import ar.edu.unq.pacman.component.Ghost;
import ar.edu.unq.pacman.component.Pacman;
import ar.edu.unq.pacman.component.Pill;
import ar.edu.unq.pacman.event.InverseModeFinishEvent;
import ar.edu.unq.pacman.event.InverseModeStartEvent;

public class GameMap extends GameScene implements ITileMapScene {

	private Pacman pacman;

	private List<Ghost> ghosts;

	private List<Pill> pills;

	private BaseTileMap tileMap;

	private int rows;

	private int columns;

	private ITileMapResourceProvider tileMapResourceProvider;

	private boolean[][] path;

	private int pacmanInitRow;

	private int pacmanInitColumn;

	private Timer inverseModeTimer;

	@Property("cell.size")
	public static int CELL_SIZE;

	@Property("inversemode.duration")
	public static int INVERSE_MODE_DURATION;

	public GameMap(int rows, int columns) throws IOException {
		super();
		this.path = new boolean[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.ghosts = new ArrayList<Ghost>();
		this.pills = new ArrayList<Pill>();
		this.inverseModeTimer = new Timer(INVERSE_MODE_DURATION, new InverseModeFinishEvent());
		this.initializeTileMap();
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
		this.addComponent(this.inverseModeTimer);

		this.pacman = new Pacman(this.pacmanInitRow, this.pacmanInitColumn);
		this.addComponent(this.pacman);
		this.addComponents(this.ghosts);
		this.addComponents(this.pills);
		this.addComponent(((PacmanGame) this.getGame()).getGameStatus().getScore());
		this.addComponent(((PacmanGame) this.getGame()).getGameStatus().getLifeCounter());
	}

	public void setPacmanInitialPos(int row, int column) {
		this.pacmanInitRow = row;
		this.pacmanInitColumn = column;
	}

	public Pacman getPacman() {
		return this.pacman;
	}

	public void addPill(Pill pill) {
		this.pills.add(pill);
	}

	public void destroyPill(Pill pill) {
		pill.destroy();
		this.pills.remove(0);
		this.fire(new ScoreUpEvent());
		if (this.pills.isEmpty()) {
			this.getGame().setCurrentScene(new PacmanWinScene());
		}
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
		try {
			return this.path[(row + this.rows) % this.rows][(column + this.columns) % this.columns];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	@Events.Fired(InverseModeStartEvent.class)
	protected void inverseModeStart(InverseModeStartEvent event) {
		this.inverseModeTimer.reset();
		this.inverseModeTimer.start();
	}

	@Override
	public List<Node> adjacents(Node node) {
		final List<Node> adjacents = new ArrayList<Node>();
		final int r = node.row();
		final int c = node.column();
		if (this.isAccessible(r - 1, c)) {
			adjacents.add(new Node(r - 1, c, 0));
		}
		if (this.isAccessible(r + 1, c)) {
			adjacents.add(new Node(r + 1, c, 0));
		}
		if (this.isAccessible(r, c - 1)) {
			adjacents.add(new Node(r, c - 1, 0));
		}
		if (this.isAccessible(r, c + 1)) {
			adjacents.add(new Node(r, c + 1, 0));
		}
		return adjacents;
	}

	@Override
	public TileMap getTileMap() {
		return this.tileMap;
	}

	@Override
	public void addTileBackground(TileMapBackground tileMapBackGround) {
		tileMapBackGround.setX(CELL_SIZE / 2);
		tileMapBackGround.setY(CELL_SIZE / 2);
		this.addComponent(tileMapBackGround);
	}

	public void addGhost(int row, int column, String name) {
		this.ghosts.add(new Ghost(row, column, name));
	}

	public void pacmanDie() {
		GameStatus status = ((PacmanGame) this.getGame()).getGameStatus();
		status.getLifeCounter().lossLife();
		if (status.getLifeCounter().isDead()) {
			this.getGame().setCurrentScene(new PacmanGameOverScene());
		} else {
			this.pacman.reset();
			for (Ghost ghost : this.ghosts) {
				ghost.reset();
			}
		}
		this.unlock();
	}

	public void lock() {
		this.setLock(true);
	}

	public void unlock() {
		this.setLock(false);
	}

	public void setLock(boolean lock) {
		this.pacman.lock = lock;
		for (Ghost ghost : this.ghosts) {
			ghost.lock = lock;
		}
	}

	public void ghostDie(Ghost ghost) {
		this.fire(new ScoreUpEvent());
		ghost.die();
	}

	public List<Pill> getPills() {
		return this.pills;
	}
}