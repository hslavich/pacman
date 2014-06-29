package ar.edu.unq.pacman.scene;

import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.pacman.PacmanGame;

public class PacmanGameOverScene extends MenuGameScene {

	@Override
	protected void addButtons(MenuBuilder menuBuilder) {
		menuBuilder.button("main.play", new GameResumeEvent());
	}

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo2.png";
	}

	@Override
	protected Font font() {
		return PacmanGame.font;
	}

	@Override
	protected void resume(final GameResumeEvent event) {
		final Game game = this.getGame();
		game.startGame();
	}

}
