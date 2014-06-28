package ar.edu.unq.pacman.scene;

import java.awt.Color;
import java.awt.Font;

import ar.edu.unq.americana.Game;
import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.components.Text;
import ar.edu.unq.americana.game.events.GameResumeEvent;
import ar.edu.unq.americana.scenes.menu.MenuGameScene;
import ar.edu.unq.americana.utils.ResourcesUtils;

public class PacmanWinScene extends MenuGameScene{

	@Override
	protected void addButtons(MenuBuilder menuBuilder) {
		menuBuilder.button("main.play", new GameResumeEvent());

	}
	
	@Override
	protected void addExtras() {

		final Text<PacmanWinScene> label = new Text<PacmanWinScene>(
				"You Win", this.font(), Color.WHITE);
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
		return ResourcesUtils.getFont("assets/fonts/Bombardier.ttf", Font.TRUETYPE_FONT, Font.BOLD, 50);
	}

	@Override
	protected void resume(final GameResumeEvent event) {
		final Game game = this.getGame();
		game.startGame();
	}	
	
}
