import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.res.Resources;

@RunWith(JUnitParamsRunner.class)
public class JSONUtilsTest {

	public Object[] provideGetString() {
		return new Object[]{
				new Object[]{ "invalid", false, false, null, "" },
				new Object[]{ "nullValue", true, true, null, "" },
				new Object[]{ "nullStringValue", true, false, "null", "" },
				new Object[]{ "valid", true, false, "valid", "valid" }
		};
	}

	@Test
	@Parameters(method = "provideGetString")
	public void testGetString(String input, boolean hasKey, boolean isNull, String output, String expected) {
		JSONUtils utils = new JSONUtils();
		Class<JSONObject> classToMock = JSONObject.class;

		JSONObject mock = mock(classToMock);		
		when(mock.has(input)).thenReturn(hasKey);
		when(mock.isNull(input)).thenReturn(isNull);
		when(mock.getString(input)).thenReturn(output);

		String actual = utils.getString(input, mock);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetStringWithException() {
		JSONUtils utils = new JSONUtils();
		Class<JSONObject> classToMock = JSONObject.class;
		
		JSONObject mock = mock(classToMock);
		when(mock.has("")).thenThrow(new JSONException("asdf"));

		String actual = utils.getString("", mock);
		String expected = "";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFromJsonFileToString() throws IOException {
		JSONUtils utils = new JSONUtils();
		int jsonResId = 1;
		String expected = "expected";
		Context contextMock = mock(Context.class);
		Resources resourcesMock = mock(Resources.class);
		InputStream inputStream = new ByteArrayInputStream(expected.getBytes());
		when(resourcesMock.openRawResource(jsonResId)).thenReturn(inputStream);
		when(contextMock.getResources()).thenReturn(resourcesMock);
		String actual = utils.fromJsonFileToString(contextMock, jsonResId);
		assertEquals(expected, actual);
	}
	
}
