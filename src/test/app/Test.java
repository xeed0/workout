package test.app;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Test extends Activity {

	private TextView v;
	private static final String TAG = "Test";
	private static final String URL = "http://www.xeed.me/json/index.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		v = (TextView) findViewById(R.id.title);
		v.setText("Doing...");
		
		// JSON object to hold the information, which is sent to the server
		HttpRequest httpRequest = new HttpRequest();
		v.setText(httpRequest.sendPost(URL, "asdf=asdf"));
		// Add key/value pairs
		
	}
}