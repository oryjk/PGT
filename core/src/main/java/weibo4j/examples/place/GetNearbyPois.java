package weibo4j.examples.place;

import java.util.List;

import weibo4j.Place;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Places;
import weibo4j.model.WeiboException;

public class GetNearbyPois {
	public static void main(String[] args) {
		String access_token = args[0];
		String lat = args[1];
		String lon = args[2];
		Place p = new Place(access_token);
		try {
			List<Places> list = p.nearbyPois(lat, lon);
			for (Places pl : list) {
				Log.logInfo(pl.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
