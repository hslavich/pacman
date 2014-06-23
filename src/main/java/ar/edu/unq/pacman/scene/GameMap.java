package ar.edu.unq.pacman.scene;

import java.io.IOException;
import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.utils.Vector2D;
import ar.edu.unq.pacman.component.Dot;
import ar.edu.unq.pacman.component.Pacman;

public class GameMap extends GameScene {
	
	private Pacman pacman;
	private MapDescriptor mapDescriptor;
	private String levelName;

	public GameMap(String levelName) throws IOException {
		super();
		this.levelName = levelName;
		this.mapDescriptor = new MapDescriptor(this.levelName);
		this.setBackground();
	}
	
	private void setBackground() {
		this.addComponent(this.mapDescriptor.getBackgroundImage());
	}

	@Override
	protected void addGameComponents() {
		super.addGameComponents();
		
		this.pacman = new Pacman(this.mapDescriptor.getPacmanInitialPos());
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
}