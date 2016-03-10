package org.nlavee.skidmore.webapps.web.api;

import java.util.ArrayList;

import org.nlavee.skidmore.webapps.web.model.NewsObj;

public interface NewsInterface {

	public ArrayList<NewsObj> chooseSections(String[] sections);
}
