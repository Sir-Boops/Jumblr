package me.boops.jumblr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class BlogFollowers {

	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;

	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;

	// Tumblr Optional Params
	private int tumblr_optional_limit = -1;
	private int tumblr_optional_offset = -1;
	
	//Set the API optional options
	public void setFollowerLimit(int limit){
		this.tumblr_optional_limit = limit;
	}
	
	public void setFollowerOffset(int offset){
		this.tumblr_optional_offset = offset;
	}

	// Define Tumblr vars
	private int tumblr_total_followers;
	private int tumblr_response_length;
	private String tumblr_msg;

	// Define Tumblr lists
	private List<String> tumblr_follower_following = new ArrayList<String>();
	private List<String> tumblr_follower_name = new ArrayList<String>();
	private List<Long> tumblr_follower_updated = new ArrayList<Long>();
	private List<String> tumblr_follower_url = new ArrayList<String>();

	// Return HTTP code
	public int getHTTPCode() {
		return this.httprescode;
	}

	// Return JSON Dump
	public String getJSON() {
		return this.json_dump;
	}

	// Return the Tumblr vars
	public int getTotalFollowers() {
		return this.tumblr_total_followers;
	}

	public int getResponseLength() {
		return this.tumblr_response_length;
	}

	public String getResponseMSG() {
		return this.tumblr_msg;
	}

	// Return following user ststus
	public boolean amFollowingUser(int user) {
		if (this.tumblr_follower_following.get(user).equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}

	}
	
	//Return follower name
	public String getFollowerName(int user){
		return this.tumblr_follower_name.get(user);
	}
	
	//Return last follower update
	public Long getFollowerLastActive(int user){
		return this.tumblr_follower_updated.get(user);
	}
	
	//Return follower URL
	public String getFollowerURL(int user){
		return this.tumblr_follower_url.get(user);
	}
	
	//Return the lists for raw usage
	public List<String> amFollowingUserList(){
		return this.tumblr_follower_following;
	}
	
	public List<String> getFollowerNameList(){
		return this.tumblr_follower_name;
	}
	
	public List<Long> getFollowerLastActiveList(){
		return this.tumblr_follower_updated;
	}
	
	public List<String> getFollowerURLList(){
		return this.tumblr_follower_url;
	}

	// Master call that sets the needed api keys
	public BlogFollowers(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}

	public void getFollowers(String blog) {

		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
		
		//Build The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/followers";
		
		//Check if the options are set
		if(this.tumblr_optional_limit >= 0){
			url += "?limit=" + this.tumblr_optional_limit;
		}
		
		if(this.tumblr_optional_offset >= 0){
			url+= "?offset=" + this.tumblr_optional_offset;
		}

		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);

		// Sign the reuqest
		try {
			consumer.sign(get);

			// Send the request
			HttpResponse res = client.execute(get);

			// Check the request status code
			if (res.getStatusLine().getStatusCode() == 200) {

				// Was able to complete the request
				this.httprescode = res.getStatusLine().getStatusCode();

				// Now parse the res
				String meta = new BasicResponseHandler().handleResponse(res);
				JSONObject json = new JSONObject(meta);

				// Dump Json
				this.json_dump = json.toString();

				// Set the static tumblr vars
				this.tumblr_msg = json.getJSONObject("meta").getString("msg");
				this.tumblr_total_followers = json.getJSONObject("response").getInt("total_users");
				this.tumblr_response_length = json.getJSONObject("response").getJSONArray("users").length();
				
				//Clear the lists
				this.tumblr_follower_following.clear();
				this.tumblr_follower_name.clear();
				this.tumblr_follower_updated.clear();
				this.tumblr_follower_url.clear();

				// Fill the lists
				for (int i = 0; json.getJSONObject("response").getJSONArray("users").length() > i; i++) {
					this.tumblr_follower_following.add(json.getJSONObject("response").getJSONArray("users").getJSONObject(i).get("following").toString());
					this.tumblr_follower_name.add(json.getJSONObject("response").getJSONArray("users").getJSONObject(i).getString("name"));
					this.tumblr_follower_updated.add(json.getJSONObject("response").getJSONArray("users").getJSONObject(i).getLong("updated"));
					this.tumblr_follower_url.add(json.getJSONObject("response").getJSONArray("users").getJSONObject(i).getString("url"));
				}

			} else {

				// Was not able to complete request
				this.httprescode = res.getStatusLine().getStatusCode();

			}

		} catch (ClientProtocolException e) {
			if (e.getMessage().toLowerCase() == "not found") {
				this.httprescode = 404;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
