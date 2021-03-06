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

public class BlogQueue {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	//Define private post request settings
	private int set_offset = -1;
	private int set_limit = -1;
	private String set_filter = "";
	
	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;
	
	//Define posts
	private JSONArray tumblr_posts;
	
	//Define Error String
	private String err = "";
	
	//Return HTTP code
	public int getHTTPCode(){
		return this.httprescode;
	}
	
	//Return JSON Dump
	public String getJSON(){
		return this.json_dump;
	}
	
	//Return the JSON of a post to be decoded
	public String getPost(int post){
		return this.tumblr_posts.getJSONObject(post).toString();
	}
	
	public int getResPostCount(){
		return this.tumblr_posts.length();
	}
	
	//Set the request settings
	public void setOffset(int offset){
		this.set_offset = offset;
	}
	
	public void setLimit(int limit){
		this.set_limit = limit;
	}
	
	public void setFilter(String filter){
		this.set_filter = filter;
	}
	
	//Return Error
	public String getError(){
		return this.err;
	}
	
	// Master call that sets the needed api keys
	public BlogQueue(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	public void getQueue(String blog){
		
		//Empty The Error
		this.err = "";
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/posts/queue";
		
		if(this.set_offset >= 0){
			url += "?offset=" + this.set_offset;
		}
		
		if(this.set_limit >= 0){
			url += "?limit=" + this.set_offset;
		}
		
		if(!this.set_filter.isEmpty()){
			url += "?filter=" + this.set_filter;
		}
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
		HttpGet get = new HttpGet(url);
		get.setConfig(reqConfig);

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
				
				//Check if meta is JSON
				if(meta.startsWith("{")){
					
					//Catch the error if their is one
					JSONObject json = new JSONObject(meta);
						
					//Set the json array off to the private string
					this.tumblr_posts = json.getJSONObject("response").getJSONArray("posts");
						
					//Dump Json
					this.json_dump = json.toString();
					
				} else {
					
					//Meta Error
					this.err = "Meta Error";
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
