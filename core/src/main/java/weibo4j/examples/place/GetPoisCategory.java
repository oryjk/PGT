package weibo4j.examples.place;

import java.util.List;

import weibo4j.Place;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.PoisitionCategory;
import weibo4j.model.WeiboException;

public class GetPoisCategory {

	public static void main(String[] args) {
		String access_token = args[0];
		Place p = new Place(access_token);
		try {
			List<PoisitionCategory> list = p.poisCategory();
			for (PoisitionCategory pois : list) {
				Log.logInfo(pois.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
