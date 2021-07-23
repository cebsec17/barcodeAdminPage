package json.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {

    ListView listView;
    Intent intent;

    private final String TAG = "MyJson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        getJSON("http://172.28.57.24/login/json.php");

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String itemValue = (String) listView.getItemAtPosition(position);
            //String values=((TextView)view).getText().toString();

            Log.i(TAG, "Textview "+itemValue);
            //Log.i(TAG, "Textview "+values);

            intent.putExtra("TextView", itemValue);

            startActivity(intent);
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "s : "+s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        Log.i(TAG, "json : "+json);
                    }
                    Log.i(TAG, "string : "+sb.toString());

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {

        Set<String> set = new HashSet<String>();
        List<String> jsonList = new ArrayList<String>();

        String all = null;

        JSONArray jsonArray = new JSONArray(json);
        String[] webchrz = new String[jsonArray.length()];
        String[] barcodeid = new String[jsonArray.length()];

        Log.i(TAG, "JSONArray"+jsonArray.toString());

        intent = new Intent(this, barcodetypes.class);
        intent.putExtra("data", jsonArray.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            webchrz[i] = obj.getString("projektname");
            barcodeid[i] = obj.getString("pid");
            Log.i(TAG, "webchrz : "+webchrz[i]);
            Log.i(TAG, "barcodeid : "+barcodeid[i]);

        }
        Collections.addAll(set, webchrz);

        Log.i(TAG, "set : "+set);

        List<String> list = new ArrayList<String>(set);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }
}