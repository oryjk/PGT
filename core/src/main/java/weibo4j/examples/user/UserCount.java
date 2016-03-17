package weibo4j.examples.user;

import java.util.List;

import weibo4j.Users;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.UserCounts;
import weibo4j.model.WeiboException;

public class UserCount {

	public static void main(String[] args) {
		String access_token = args[0];
		String uids = args[1];
		Users um = new Users(access_token);
		try {
			List<UserCounts> user = um.getUserCount(uids);
			for (UserCounts u : user) {
				Log.logInfo(u.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
