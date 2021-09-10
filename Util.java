


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
	public static final String TAG = "UrlUtil";

	public static class UrlEntity {
		/**
		 * 基础url
		 */
		public String baseUrl;
		/**
		 * url参数
		 */
		public Map<String, String> params;
	}
//https://h5.dingtalk.com/healthAct/index.html?qrCode=V229495d37a6c75d794b87198b160b526b#/result 该方法健康码解不出
//	public static String getUrl(String url, String name) {
//		url += "&";
//		String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";
//		Pattern r = Pattern.compile(pattern);
//		Matcher matcher = r.matcher(url);
//		if (matcher.find()) {
//			//            System.out.println(matcher.group(0));
//			return matcher.group(0).split("=")[1].replace("&", "");
//		} else {
//			return null;
//		}
//	}

	/**
	 * https://h5.dingtalk.com/healthAct/index.html?qrCode=V229495d37a6c75d794b87198b160b526b#/result
	 * 解析健康码参数
	 * @param url
	 * @return
	 */
	public static Map<String, List<String>> getQueryParams(String url) {
		try {
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			String pattern = "\\?";
			String[] urlParts = url.split(pattern);
			if (urlParts.length > 1) {
				String query = urlParts[1];
				for (String param : query.split("&")) {
					String[] pair = param.split("=");
					String key = URLDecoder.decode(pair[0], "UTF-8");
					String value = "";
					if (pair.length > 1) {
						value = URLDecoder.decode(pair[1], "UTF-8");
					}

					List<String> values = params.get(key);
					if (values == null) {
						values = new ArrayList<String>();
						params.put(key, values);
					}
					values.add(value);
				}
			}

			return params;
		} catch (Exception ex) {
			TrackExceptionUtils.trackException(ex,TAG+">>"+"getQueryParams");
		}
		return null;
	}
	/**
	 * 解析url
	 *  https://h5.dingtalk.com/healthAct/index.html?qrCode=V229495d37a6c75d794b87198b160b526b#/result  报异常，解不出
	 * @param url
	 * @return
	 */
	@Deprecated
	public static UrlEntity parse(String url) {
		UrlEntity entity = null;
		try {
			entity = new UrlEntity();
			if (url == null) {
				return entity;
			}
			url = url.trim();
			if (url.equals("")) {
				return entity;
			}
			String[] urlParts = url.split("\\?");
			entity.baseUrl = urlParts[0];
			//没有参数
			if (urlParts.length == 1) {
				return entity;
			}
			//有参数
			String[] params = urlParts[1].split("&");
			entity.params = new HashMap<>();
			for (String param : params) {
				String[] keyValue = param.split("=");
				entity.params.put(keyValue[0], keyValue[1]);
			}
		} catch (Exception e) {
			e.fillInStackTrace();
			TraceUtils.getInstance().trace(Constant.TAG_RECOGNIZE, TAG, "qrcode: "+e.toString());

		}

		return entity;
	}
}
