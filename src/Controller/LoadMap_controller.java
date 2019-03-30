package Controller;

import Model.IO;
import View.LoadMap;

/**
 * <h1>LoadMap_controller</h1>
 *
 * It is an controller connect load view and io.
 * 
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class LoadMap_controller extends Object {
	LoadMap loadView;
	IO fileModel;

	/**
	 * It is a constructor.
	 *
	 */
	public LoadMap_controller() {
		fileModel = new IO();
		loadView = new LoadMap();
		fileModel.addObserver(loadView);
	}

	/**
	 * Receive file path to convert file to hash map countries, continents.
	 * 
	 * @param filePath File path.
	 */
	public void sendfilename(String filePath) {

		fileModel.readFile(filePath);

	}

}
