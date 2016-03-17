package weibo4j.examples.place;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import weibo4j.Place;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class AddPhoto {
	
	public static void main(String[] args) {
		String access_token = args[0];
		String poiid = args[1];
		String status = args[2];
		Place p = new Place(access_token);
		try {
			byte[] pic =readFileImage(args[3]);
			ImageItem item = new ImageItem(pic);
			Status s = p.addPhoto(poiid, status, item);
			Log.logInfo(s.toString());
		} catch (WeiboException e) {
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
