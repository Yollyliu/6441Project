package Controller;

import java.util.HashMap;
import javax.swing.JOptionPane;
import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.IO;
import Model.InitializePhase;
import Model.Message;
import View.InitGame;
import View.PlayView;

/**
 * <h1>InitGame_controller</h1> it is initGame controller to connect
 * InitializePharse_model, IO and initGame view
 * 
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class InitGame_controller extends Object {

	IO iomodel;
	InitializePhase pharseModel;

	/**
	 * It is a constructor.
	 */
	public InitGame_controller() {
		iomodel = new IO();
		pharseModel = new InitializePhase();
	}

	/**
	 * It receive the player number and file path.
	 * 
	 * @param num The number of players.
	 * @param filePath File path.
	 */
	public void receive(int num, String filePath) {

		// checkMap whether connected or not
		iomodel.readFile(filePath);
		HashMap<String, Country> countries = iomodel.getCountries();
		HashMap<String, Continent> continents = iomodel.getContinents();
		System.out.println(countries.size() + " " + continents.size());
		Checkmap checkmap = new Checkmap(countries, continents);
		checkmap.judge();
		boolean result = Message.isSuccess();
		if (result) {
			pharseModel.addData(num, countries, continents);
			pharseModel.initPhase();
			countries = pharseModel.getCountries();
			continents = pharseModel.getContinents();
			PlayView p = new PlayView();

			p.countries = countries;
			p.continents = continents;
			p.playerSet = pharseModel.getPlayerSet();
		} else {
			String error = Message.getMessage();
			JOptionPane.showConfirmDialog(null, error);
			new InitGame();
		}

	}
}
