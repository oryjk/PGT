package weibo4j.examples.search;

import weibo4j.Search;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;

public class SearchSuggestionsCompanies {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		Search search = new Search(access_token);
		try {
			JSONArray jo = search.searchSuggestionsCompanies(q);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
