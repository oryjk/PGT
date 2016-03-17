package weibo4j.examples.shorturl;

import weibo4j.ShortUrl;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class ShortToLongUrl {

	public static void main(String[] args) {
		String access_token = args[0];
		String url = args[1];
		ShortUrl su = new ShortUrl(access_token);
		try {
			JSONObject jo = su.shortToLongUrl(url);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}

