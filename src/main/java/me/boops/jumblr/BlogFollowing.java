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

public class BlogFollowing {
	
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
	
	//Define return args
	private int tumblr_total_following;
	private JSONArray tumblr_following_blogs;
	
	// Return HTTP code
	public int getHTTPCode() {
		return this.httprescode;
	}

	// Return JSON Dump
	public String getJSON() {
		return this.json_dump;
	}
	
	public int getTotalFollowing(){
		return this.tumblr_total_following;
	}
	
	public JSONArray getFollowingBlogs(){
		return this.tumblr_following_blogs;
	}
	
	// Master call that sets the needed api keys
	public BlogFollowing(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	public void setOffset(int offset){
		this.tumblr_optional_offset = offset;
	}
	
	// Make the call
	public void getFollowing(){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
		
		//Build The URL
		String url = "https://api.tumblr.com/v2/user/following";
		
		//Check if the options are set
		if(this.tumblr_optional_limit >= 0){
			url += "?limit=" + this.tumblr_optional_limit;
		}
		
		if(this.tumblr_optional_offset >= 0){
			url+= "?offset=" + this.tumblr_optional_offset;
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

				// Now parse the res
				String meta = new BasicResponseHandler().handleResponse(res);
				JSONObject json = new JSONObject(meta);

				// Dump Json
				this.json_dump = json.toString();
				
				
				this.tumblr_total_following = json.getJSONObject("response").getInt("total_blogs");
				this.tumblr_following_blogs = json.getJSONObject("response").getJSONArray("blogs");
				

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
	
	private String blog_name;
	private String blog_desc;
	private String blog_title;
	private long blog_updated;
	private String blog_url;
	
	public String getBlogName(){
		return this.blog_name;
	}
	
	public String getBlogDescription(){
		return this.blog_desc;
	}
	
	public String getBlogTitle(){
		return this.blog_title;
	}
	
	public long getBlogLastUpdated(){
		return this.blog_updated;
	}
	
	public String getBlogURL(){
		return this.blog_url;
	}
	
	
	public void decodeBlog(JSONObject blog){
		
		this.blog_name = blog.getString("name");
		this.blog_desc = blog.getString("description");
		this.blog_title = blog.getString("title");
		this.blog_updated = blog.getLong("updated");
		this.blog_url = blog.getString("url");
		
	}
	
}
