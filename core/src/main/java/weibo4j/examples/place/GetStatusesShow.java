package weibo4j.examples.place;

import weibo4j.Place;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class GetStatusesShow {
	public static void main(String[] args) {
		String access_token = args[0];
		String id = args[1];
		Place p = new Place(access_token);
		try {
			Status s = p.statusesShow(id);
			Log.logInfo(s.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
