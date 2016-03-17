package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;

public class ReplyComment {

	public static void main(String[] args) {
		String access_token = args[0];
		String cid = args[1];
		String id = args[2];
		String comments = args[3];
		Comments cm = new Comments(access_token);
		try {
			Comment com = cm.replyComment(cid, id, comments);
			Log.logInfo(com.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
