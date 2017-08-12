package me.boops.jumblr;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class BlogReblogPost {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	//Define private optional calls for /reblog
	private String post_type = "";
	private String post_state = "";
	private String post_comment = "";
	private boolean post_inline = false;
	private String post_tags = "";
	private String post_tweet = "";
	private String post_date = "";
	private String post_format = "";
	private String post_slug = "";
	
	//Set the optional Strings
	public void setPostComment(String comment){
		this.post_comment = comment;
	}
	public void useTumblrHosting(boolean ans){
		this.post_inline = ans;
	}
	public void setPostTags(String tags){
		this.post_tags = tags;
	}
	public void setPostTweet(String tweet){
		this.post_tweet = tweet;
	}
	public void setPostDate(String date){
		this.post_date = date;
	}
	public void setPostFormat(String format){
		this.post_format = format;
	}
	public void setPostSlug(String slug){
		this.post_slug = slug;
	}
	public void setPostType(String type){
		this.post_type = type;
	}
	public void setPostState(String state){
		this.post_state = state;
	}
	
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
	
	// Master call that sets the needed api keys
	public BlogReblogPost(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	
	public void Reblog(Long id, String reblog_key, String blog){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
		
		//Build the URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post/reblog";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
		HttpPost get = new HttpPost(url);
		get.setConfig(reqConfig);
		
		//Add the needed params
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("id", id.toString()));
		urlParameters.add(new BasicNameValuePair("reblog_key", reblog_key));
		
		//Add the optional Params
		if(!this.post_comment.isEmpty()){
			urlParameters.add(new BasicNameValuePair("comment", this.post_comment));
		}
		if(this.post_inline){
			urlParameters.add(new BasicNameValuePair("native_inline_images", "true"));
		}
		if(!this.post_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.post_tags));
		}
		if(!this.post_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.post_tweet));
		}
		if(!this.post_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.post_date));
		}
		if(!this.post_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.post_format));
		}
		if(!this.post_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.post_slug));
		}
		if(!this.post_type.isEmpty()){
			urlParameters.add(new BasicNameValuePair("type", this.post_type));
		}
		if(!this.post_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.post_state));
		}
		
		//Build the post request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		// Sign & send the reuqest
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
