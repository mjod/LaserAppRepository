package com.example.laser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JoinActivity extends Activity {

	TextView playerslist;
	EditText txtPrice;
	EditText txtDesc;
	EditText txtCreatedAt;
	Button btnSave;
	Button btnDelete;

	String pid;
	String currentPlayers;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jParser = new JSONParser();
	JSONParser jsonParser = new JSONParser();
	JSONArray products = null;


	// single product url
	private static String url_get_gameinfo = "http://128.4.201.0/laserDatabase/android_connect/get_game_info.php";
	// url to update product
	private static final String url_update_player = "http://128.4.201.0/laserDatabase/android_connect/add_player.php";

	// url to delete product

	// JSON Node names
	private static final String TAG_GAME_INFO = "game_info";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_GAMES = "gameinfo";
	private static final String TAG_PID = "game_id";
	private static final String TAG_CURRENT_PLAYERS = "current_players";
	private static final String TAG_PLAYER1 = "player1";
	private static final String TAG_PLAYER2 = "player2";
	private static final String TAG_PLAYER3 = "player3";
	private static final String TAG_PLAYER4 = "player4";
	private static final String TAG_PLAYER5 = "player5";
	private static final String TAG_PLAYER6 = "player6";
	private static final String TAG_PLAYER7 = "player7";
	private static final String TAG_PLAYER8 = "player8";
	private static String Allplayers = "";
	boolean temp = true;





	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		Bundle bundle = getIntent().getExtras();
		pid = bundle.getString("gamepid");
		currentPlayers = bundle.getString("current_players");
		String player1 = bundle.getString("player1");

		TextView players = (TextView)findViewById(R.id.playerslist);
		players.setTextColor(Color.RED);
		players.setText(Allplayers);
		while(true){
			if(temp == true){
				temp = false;
				new LoadPlayers().execute();
			}
		}
		// Getting complete product details in background thread
		//new UpdateGameInfo().execute();
		//new LoadPlayers().execute();

	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class LoadPlayers extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/*
			pDialog = new ProgressDialog(JoinActivity.this);
			pDialog.setMessage("Loading Game Lobby. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			 */
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("game_id", pid));

			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_get_gameinfo, "GET", params1);


			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_GAME_INFO);

					// looping through All Products

					JSONObject c = products.getJSONObject(0);

					// Storing each json item in variable
					Allplayers = c.getString("player1");
					Allplayers = Allplayers + "\n" + c.getString("player2");
					Allplayers = Allplayers + "\n" + c.getString("player3");
					Allplayers = Allplayers + "\n" + c.getString("player4");
					Allplayers = Allplayers + "\n" + c.getString("player5");
					Allplayers = Allplayers + "\n" + c.getString("player6");
					Allplayers = Allplayers + "\n" + c.getString("player7");
					Allplayers = Allplayers + "\n" + c.getString("player8");



					// Starting new intent


				}
				else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}



		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			//pDialog.dismiss();
			temp = true;
		}
	}

	/**
	 * Background Async Task to  Save product Details
	 * */
	class UpdateGameInfo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(JoinActivity.this);
			pDialog.setMessage("Update Info");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_CURRENT_PLAYERS, currentPlayers));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_player,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				/*
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
				} else {
					// failed to update product
				}
				 */
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}


}
