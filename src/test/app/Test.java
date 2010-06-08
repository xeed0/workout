package test.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends Activity {

	private TextView v;
	private static final String URL = "http://www.xeed.me/json/index.php?data=true";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		v = (TextView) findViewById(R.id.title);
		v.setText("Doing...");
		
		// JSON object to hold the information, which is sent to the server
		HttpRequest httpRequest = new HttpRequest();
		Map<String, List> jsonMap = new HashMap<String, List>();
		List<String> myList = new ArrayList<String>();
		JSONObject jo = null;
		try {	
			myList.add("And");
			myList.add("Your");
			
			JSONArray ja = new JSONArray(myList.toString());
			
			jo = new JSONObject(jsonMap);

			jo.put("data", ja);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = httpRequest.sendJSONPost(URL, jo);
		v.setText(response);
		
	}
}