import static org.junit.Assert.assertEquals;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class HelloTest {

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
		HelloWorld helloWorld = new HelloWorld();
		Class<JSONObject> classToMock = JSONObject.class;

		JSONObject mock = EasyMock.createNiceMock(classToMock);		
		EasyMock.expect(mock.has(input)).andReturn(hasKey);
		EasyMock.expect(mock.isNull(input)).andReturn(isNull);
		EasyMock.expect(mock.getString(input)).andReturn(output);
		EasyMock.replay(mock);
		String actual = helloWorld.getString(input, mock);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetStringWithException() {
		HelloWorld helloWorld = new HelloWorld();
		Class<JSONObject> classToMock = JSONObject.class;
		
		JSONObject mock = EasyMock.createNiceMock(classToMock);
		EasyMock.expect(mock.has("")).andThrow(new JSONException("asdf"));
		EasyMock.replay(mock);
		String actual = helloWorld.getString("", mock);
		String expected = "";
		assertEquals(expected, actual);
	}
	

	
}
