package weibo4j.examples.account;

import java.util.List;

import weibo4j.Account;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.School;
import weibo4j.model.WeiboException;

public class GetAccountProfileSchoolList {

	public static void main(String[] args) {
		String access_token = args[0];
		Account am = new Account(access_token);
		String province = args[1];
		String capital = args[2];
		try {
			List<School> schools = am.getAccountProfileSchoolList(province,
					capital);
			for (School school : schools) {
				Log.logInfo(school.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
