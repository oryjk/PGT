package weibo4j.examples.location;

import java.util.List;

import weibo4j.Location;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Poisition;
import weibo4j.model.WeiboException;

public class SearchPoisByArea {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		String coordinates = args[2];
		Location l = new Location(access_token);
		try {
			List<Poisition> list = l.searchPoisByAreaByQ(q, coordinates);
			for (Poisition p : list) {
				Log.logInfo(p.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
