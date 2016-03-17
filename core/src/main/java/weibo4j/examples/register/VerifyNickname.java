package weibo4j.examples.register;

import weibo4j.Register;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

public class VerifyNickname {

	public static void main(String[] args) {
		String access_token = args[0];
		String nickname = args[1];
		Register reg = new Register(access_token);
		try {
			JSONObject json = reg.verifyNickname(nickname);
			System.out.println(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
