package Controller;

import java.util.HashMap;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.IO;
import View.EditMap;
/**
 *<h1>EditMap_contrallor</h1>
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */

public class EditMap_controllor extends Object{
	EditMap createMapView;
	Checkmap checkModel;
	
	/**
	 * It is a constructor.
	 */
	public EditMap_controllor(){
	
		createMapView = new EditMap();
	
	}
	
	/**
	 * It invoke IO class.
	 * @param Countries country.
	 * @param continents continent.
	 */
	public void writefile(HashMap<String, Country> Countries, HashMap<String, Continent> continents){
		
	}

}
