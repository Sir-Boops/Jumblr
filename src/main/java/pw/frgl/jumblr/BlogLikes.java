package pw.frgl.jumblr;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class BlogLikes {

	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;

	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;
	
	//Define Strings You Can Set
	private int set_limit = -1;
	private int set_offset = -1;
	private long set_before_time = -1;
	private long set_after_time = -1;
	
	//Define private tumblr vars
	private int tumblr_like_count;
	private int tumblr_like_res_count;
	
	//Define private posts JSONArray
	private JSONArray tumblr_liked_posts;
	
	//Return HTTP code
	public int getHTTPCode(){
		return this.httprescode;
	}
	
	//Return JSON Dump
	public String getJSON(){
		return this.json_dump;
	}
	
	//Return Tumblr Ints
	public int getTotalLikeCount(){
		return this.tumblr_like_count;
	}
	
	public int getPostLength(){
		return this.tumblr_like_res_count;
	}
	
	//Return liked post as a String for the post decoder
	public String getLikedPost(int post){
		return this.tumblr_liked_posts.getJSONObject(post).toString();
	}

	// Master call that sets the needed api keys
	public BlogLikes(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	//Set The Optional Settings
	public void setLimit(int limit){
		this.set_limit = limit;
	}
	
	public void setOffset(int offset){
		this.set_offset = offset;
	}
	
	public void setBeforeTime(long before){
		this.set_before_time = before;
	}
	
	public void setAfterTime(long after){
		this.set_after_time = after;
	}

	public void getLikes(String blog) {

		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
		
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/likes";
		
		//Check if we should add any of the options
		if(this.set_limit >= 0){
			url += "?limit=" + this.set_limit;
		}
		
		if(this.set_offset >= 0){
			url += "?offset=" + this.set_offset;
		}
		
		if(this.set_before_time >= 0){
			url += "?before=" + this.set_before_time;
		}
		
		if(this.set_after_time >= 0){
			url += "?after" + this.set_after_time;
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

				//Now parse the res
				String meta = new BasicResponseHandler().handleResponse(res);
				JSONObject json = new JSONObject(meta);
				
				//Set the two liked counts
				this.tumblr_like_count = json.getJSONObject("response").getInt("liked_count");
				this.tumblr_like_res_count = json.getJSONObject("response").getJSONArray("liked_posts").length();
				
				//Set JSONArray
				this.tumblr_liked_posts = json.getJSONObject("response").getJSONArray("liked_posts");
				
				//Dump Json
				this.json_dump = json.toString();

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
