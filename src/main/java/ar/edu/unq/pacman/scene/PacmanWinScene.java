package ar.edu.unq.pacman.scene;

import java.awt.Color;
import java.awt.Font;

import ar.edu.unq.americana.components.Text;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.pacman.PacmanGame;

public class PacmanWinScene extends MenuGameScene {

	@Override
	protected void addButtons(MenuBuilder menuBuilder) {
		menuBuilder.button("main.play", new GameResumeEvent());

	}

	@Override
	protected void addExtras() {
		final Text<PacmanWinScene> label = new Text<PacmanWinScene>("Level completed", this.font(), Color.WHITE);
		label.alignBottomTo(300);
		label.alignRightTo(325);
		this.addComponent(label);
	}

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo.jpg";
	}

	@Override
	protected Font font() {
		return PacmanGame.font;
	}

	@Override
	protected void resume(final GameResumeEvent event) {
		PacmanGame game = (PacmanGame) this.getGame();
		game.nextLevel();
	}

}
