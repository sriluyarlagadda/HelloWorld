import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;

public class HelloWorld {

	public String getString(String key, JSONObject json) {
		try {
			if (!json.has(key)) {
				return "";
			}

			if (json.isNull(key)) {
				return "";
			}
			
			String value = json.getString(key);
			if (value.equalsIgnoreCase("null")) {
				return "";
			}
			return value;
		} catch (JSONException e) {
			return "";
		}
	}

	public static String fromJsonFileToString(Context context, int jsonResId) throws IOException {
		Resources res = context.getResources();
		InputStream inputStream = res.openRawResource(jsonResId);

		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		
		try {
			InputStreamReader bufferedReader = new InputStreamReader(inputStream, "UTF-8");
			Reader reader = new BufferedReader(bufferedReader);
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			inputStream.close();
		}
		
		return writer.toString();
	}
}
 