package json.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class barcodetypes extends AppCompatActivity {

    ListView listView;
    String json, pid, text;

    private final String TAG = "MyJson";
    private String URL = "http://172.28.57.24/login/json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcodetypes);

        listView = findViewById(R.id.listViewBarcode);

        //json = getIntent().getStringExtra("data");
        //pid = getIntent().getStringExtra("pid");

        text = getIntent().getStringExtra("TextView");

        Log.i(TAG, "TeXT " + text);

        //getJSON("http://172.28.57.24/login/json2.php");
       // tradeUmi();
        //volleyPost();

        String postUrl = "http://172.28.57.24/login/json2.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("text", text);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "postdata "+postData);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, response -> {
            Log.i(TAG, "posturl " + postUrl);
            Log.i(TAG, "postdata " + postData);
            Log.i(TAG, "response " + response);

            Log.i(TAG, "response " + response);

            System.out.println(response);
        }, error -> error.printStackTrace());

        Log.i(TAG, "JSON Object "+jsonObjectRequest.toString());

        requestQueue.add(jsonObjectRequest);
    }

    /*private void getProjects(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                if (response.equals("Success")) {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Failure")) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show())
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("text", text);
                    Log.i(TAG, ""+data);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }*/

    public void volleyPost(){
        String postUrl = "http://172.28.57.24/login/json2.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("text", text);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, response -> {
            Log.i(TAG, "posturl " + postUrl);
            Log.i(TAG, "postdata " + postData);
            Log.i(TAG, "response " + response);

            Log.i(TAG, "response " + response);

            System.out.println(response);
        }, error -> error.printStackTrace());

        Log.i(TAG, "JSON Object "+jsonObjectRequest.toString());

        requestQueue.add(jsonObjectRequest);
    }

   /*private void getJSON(final String urlWebService) {

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
                    java.net.URL url = new URL(urlWebService);
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
        JSONArray jsonArray = new JSONArray(json);
        String[] webchrz = new String[jsonArray.length()];

        Log.i(TAG, "JSONArray"+jsonArray.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            webchrz[i] = obj.getString("barcodetype");;
            Log.i(TAG, "webchrz : "+webchrz[i]);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, webchrz);
        listView.setAdapter(arrayAdapter);
    }

    private void tradeUmi(){
        if(!text.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                if (response.equals("Success")) {
                    Toast.makeText(this, "Yay", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Failure")) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show())
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    Log.i(TAG, "text : "+text);
                    data.put("text", text);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }*/
}