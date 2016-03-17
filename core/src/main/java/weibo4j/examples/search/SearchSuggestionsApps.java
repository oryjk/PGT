package weibo4j.examples.search;

import weibo4j.Search;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;

public class SearchSuggestionsApps {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		int count = Integer.parseInt(args[2]);
		Search search = new Search(access_token);
		try {
			JSONArray jo = search.searchSuggestionsApps(q, count);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
