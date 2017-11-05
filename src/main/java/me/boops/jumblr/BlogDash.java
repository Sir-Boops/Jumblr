package me.boops.jumblr;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class BlogDash {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;

	// Define private HTTP strings
	private int httprescode;
	
	//Define JSON dump
	private String json_dump;
	
	// Optional strings
	private int requestLimit = 20;
	private int requestOffset = 0;
	private String requestType = null;
	private long reuqestSince = 0;
	private boolean requestReblog = false;
	private boolean requestNotes = false;
	
	// Private res strings
	private int tumblrPosts = 0;
	private JSONArray posts = null;
	
	// Allow setting of the options
	
	public void setRequestLimit(int limit) {
		this.requestLimit = limit;
	}
	
	public void setRequestOffset(int offset) {
		this.requestOffset = offset;
	}
	
	public void setRequestPostType(String type) {
		this.requestType = type;
	}
	
	public void setRequestSince(long id) {
		this.reuqestSince = id;
	}
	
	public void setRequestReblog(boolean reblog) {
		this.requestReblog = reblog;
	}
	
	public void setRequestNotes(boolean notes) {
		this.requestNotes = notes;
	}
	
	// Respond request string
	public int getTumblrPostCount() {
		return this.tumblrPosts;
	}
	
	public JSONArray getTumblrPosts() {
		return this.posts;
	}
	
	public int getHTTPCode() {
		return this.httprescode;
	}
	
	public String getJSONDump() {
		return this.json_dump;
	}
	
	// Master call that sets the needed api keys
		public BlogDash(String cust_key, String cust_sec, String token, String token_sec) {

			// Set the required oauth strings
			this.cust_key = cust_key;
			this.cust_sec = cust_sec;
			this.token = token;
			this.token_sec = token_sec;

		}
		
		public void getPosts(){
			
			// Define oauth
			OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
			consumer.setTokenWithSecret(this.token, this.token_sec);
					
			//Create The URL
			String url = "https://api.tumblr.com/v2/user/dashboard";
			
			// Setup The Request
			HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
			RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
			HttpGet get = new HttpGet(url);
			get.setConfig(reqConfig);
			
			//Add options if they are set
			if(this.requestLimit > 0){
				url += "?limit=" + this.requestLimit;
			}
			
			if(this.requestOffset >= 0){
				url += "&id=" + this.requestOffset;
			}
			
			if(!this.requestType.isEmpty()){
				url += "&tag=" + this.requestType;
			}
			
			if(this.reuqestSince >= 0){
				url += "&offset=" + this.reuqestSince;
			}
			
			if(this.requestReblog){
				url += "&reblog_info=true";
			}
			
			if(this.requestNotes){
				url += "&notes_info=true";
			}

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
					
					//Dump Json
					this.json_dump = json.toString();
					
					//Set the responce into the private area
					this.tumblrPosts = json.getJSONObject("response").getJSONArray("posts").length();
					this.posts = json.getJSONObject("response").getJSONArray("posts");

				} else {

					// Was not able to complete request
					this.httprescode = res.getStatusLine().getStatusCode();

				}

			} catch (ClientProtocolException e) {
				if (e.getMessage().toLowerCase() == "not found") {
					this.httprescode = 404;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

}
