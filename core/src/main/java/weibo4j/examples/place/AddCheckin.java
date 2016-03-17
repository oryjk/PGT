package weibo4j.examples.place;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import weibo4j.Place;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class AddCheckin {

	public static void main(String[] args) {
		try {
			String access_token = args[0];
			String poiid = args[1];
			String status = java.net.URLEncoder.encode(args[2], "UTF-8");
			byte[] pic = readFileImage(args[3]);
			Place p = new Place(access_token);
			ImageItem item = new ImageItem(pic);
			Status s = p.addCheckin(poiid, status, item);
			Log.logInfo(s.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] readFileImage(String filename) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filename));
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}

}
