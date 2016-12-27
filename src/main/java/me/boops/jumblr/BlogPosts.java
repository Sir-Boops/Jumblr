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

public class BlogPosts {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	//Define private optional settings
	private String set_post_type = "";
	private long set_post_id = -1;
	private String set_post_tag = "";
	private int set_limit = -1;
	private int set_offset = -1;
	private boolean set_reblog_info = false;
	private boolean set_notes_info = false;
	private String set_filter = "";
	
	//Define Tumblr Reponses
	private int tumblr_total_posts;
	private int tumblr_res_posts;
	private JSONArray tumblr_posts;
	
	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;
	
	//Return HTTP code
	public int getHTTPCode(){
		return this.httprescode;
	}
	
	//Return JSON Dump
	public String getJSON(){
		return this.json_dump;
	}
	
	//Return Tumblr Reponses
	public int getTotalPosts(){
		return this.tumblr_total_posts;
	}
	
	public int getResPosts(){
		return this.tumblr_res_posts;
	}
	
	public String getPost(int post){
		//Check if it's null
		if(this.tumblr_posts == null){
			return null;
		} else {
			return this.tumblr_posts.getJSONObject(post).toString();
		}
	}
	
	//Set the optional Settings
	public void setPostType(String type){
		this.set_post_type = type;
	}
	
	public void setPostID(long id){
		this.set_post_id = id;
	}
	
	public void setPostTag(String tag){
		this.set_post_tag = tag;
	}
	
	public void setLimit(int limit){
		this.set_limit = limit;
	}
	
	public void setOffset(int offset){
		this.set_offset = offset;
	}
	
	public void setShowReblogInfo(boolean ans){
		this.set_reblog_info = ans;
	}
	
	public void setShowNotesInfo(boolean ans){
		this.set_notes_info = ans;
	}
	
	public void setPostFilter(String filter){
		this.set_filter = filter;
	}
	
	// Master call that sets the needed api keys
	public BlogPosts(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	public void getPosts(String blog){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/posts";
		
		//Add options if they are set
		if(!this.set_post_type.isEmpty()){
			url += "?type=" + this.set_post_type;
		}
		
		if(this.set_post_id >= 0){
			url += "?id=" + this.set_post_id;
		}
		
		if(!this.set_post_tag.isEmpty()){
			url += "?tag=" + this.set_post_tag;
		}
		
		if(this.set_limit >= 0){
			url += "?limit=" + this.set_limit;
		}
		
		if(this.set_offset >= 0){
			url += "?offset=" + this.set_offset;
		}
		
		if(this.set_reblog_info){
			url += "?reblog_info=true";
		}
		
		if(this.set_notes_info){
			url += "?notes_info=true";
		}
		
		if(!this.set_filter.isEmpty()){
			url += "?filter=" + this.set_filter;
		}
		
		//Setup the request config
		int TimeOutMilli = (10 * 1000);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TimeOutMilli).setSocketTimeout(TimeOutMilli).setConnectTimeout(TimeOutMilli).build();
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);

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
				this.tumblr_total_posts = json.getJSONObject("response").getInt("total_posts");
				this.tumblr_res_posts = json.getJSONObject("response").getJSONArray("posts").length();
				this.tumblr_posts = json.getJSONObject("response").getJSONArray("posts");

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
