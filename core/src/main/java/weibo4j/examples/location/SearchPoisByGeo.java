package weibo4j.examples.location;

import weibo4j.Location;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class SearchPoisByGeo {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		String cenname = args[2];
		Location l = new Location(access_token);
		try {
			JSONObject json = l.searchPoisByGeoByCenname(q, cenname);
			Log.logInfo(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
