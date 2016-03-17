package weibo4j.examples.friendships;

import weibo4j.Friendships;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class GetFriendshipsById {

	public static void main(String[] args) {
		String access_token = args[0];
		long source = Long.parseLong(args[1]);
		long target = Long.parseLong(args[2]);
		Friendships fm = new Friendships(access_token);
		try {
			JSONObject json = fm.getFriendshipsById(source, target);
			Log.logInfo(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
