package ar.edu.unq.pacman.scene;

import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.americana.utils.ResourcesUtils;

public class PacmanGameOverScene extends MenuGameScene {

	@Override
	protected void addButtons(MenuBuilder menuBuilder) {
		menuBuilder.button("main.play", new GameResumeEvent());
		// menuBuilder.button("main.exit", new GameCloseEvent());

	}

	@Override
	protected String logoResourcePath() {
		return "assets/menues/logo2.png";
	}

	@Override
	protected Font font() {
		return ResourcesUtils.getFont("assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT, Font.BOLD, 50);
	}

	@Override
	protected void resume(final GameResumeEvent event) {
		final Game game = this.getGame();
		game.startGame();
	}

}
