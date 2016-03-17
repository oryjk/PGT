package weibo4j.examples.search;

import java.util.List;

import weibo4j.Search;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.SchoolSearch;
import weibo4j.model.WeiboException;

public class SearchSuggestionsSchools {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		Search search = new Search(access_token);
		try {
			List<SchoolSearch> list = search.searchSuggestionsSchools(q);
			for (SchoolSearch ss : list) {
				Log.logInfo(ss.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
