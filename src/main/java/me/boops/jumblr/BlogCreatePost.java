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

public class BlogCreatePost {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	//Define private settings
	private String set_state = "";
	private String set_tags = "";
	private String set_tweet = "";
	private String set_date = "";
	private String set_format = "";
	private String set_slug = "";
	
	//Set the optional settings
	public void setPostState(String state){
		this.set_state = state;
	}
	
	public void setPostTags(String tags){
		this.set_tags = tags;
	}
	
	public void setTweet(String tweet){
		this.set_tweet = tweet;
	}
	
	public void setDate(String date){
		this.set_date = date;
	}
	
	public void setFormat(String format){
		this.set_format = format;
	}
	
	public void setSlug(String slug){
		this.set_slug = slug;
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
	public BlogCreatePost(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	//Post a text post
	public void createTextPost(String blog, String title, String body){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
		HttpPost get = new HttpPost(url);
		get.setConfig(reqConfig);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "text"));
		if(title != null){
			urlParameters.add(new BasicNameValuePair("title", title));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		urlParameters.add(new BasicNameValuePair("body", body));
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post a quote post
	public void createQuotePost(String blog, String quote, String source){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "quote"));
		urlParameters.add(new BasicNameValuePair("quote", quote));
		if(source != null){
			urlParameters.add(new BasicNameValuePair("source", source));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post a chat post
	public void createChatPost(String blog, String title, String conversation){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "chat"));
		urlParameters.add(new BasicNameValuePair("conversation", conversation));
		if(title != null){
			urlParameters.add(new BasicNameValuePair("title", title));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post a photo post
	public void createPhotoPost(String blog, String caption, String link, String source, String data){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "photo"));
		if(caption != null){
			urlParameters.add(new BasicNameValuePair("caption", caption));
		}
		if(link != null){
			urlParameters.add(new BasicNameValuePair("link", link));
		}
		if(source != null){
			urlParameters.add(new BasicNameValuePair("source", source));
		}
		if(data != null){
			urlParameters.add(new BasicNameValuePair("data", data));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post a link post
	public void createLinkPost(String blog, String title, String link_url, String description, String thumbnail, String excerpt, String author){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "link"));
		urlParameters.add(new BasicNameValuePair("url", link_url));
		if(description != null){
			urlParameters.add(new BasicNameValuePair("description", description));
		}
		if(thumbnail != null){
			urlParameters.add(new BasicNameValuePair("thumbnail", thumbnail));
		}
		if(excerpt != null){
			urlParameters.add(new BasicNameValuePair("excerpt", excerpt));
		}
		if(author != null){
			urlParameters.add(new BasicNameValuePair("author", author));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post an audio post
	public void createAudioPost(String blog, String caption, String external_url, String data){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "audio"));
		if(caption != null){
			urlParameters.add(new BasicNameValuePair("caption", caption));
		}
		if(external_url != null){
			urlParameters.add(new BasicNameValuePair("external_url", external_url));
		}
		if(data != null){
			urlParameters.add(new BasicNameValuePair("data", data));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
	
	//Post a video post
	public void createVideoPost(String blog, String caption, String embed, String data){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
				
		//Create The URL
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/post";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpPost get = new HttpPost(url);
		
		//Build the post request
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("type", "video"));
		if(caption != null){
			urlParameters.add(new BasicNameValuePair("caption", caption));
		}
		if(embed != null){
			urlParameters.add(new BasicNameValuePair("embed", embed));
		}
		if(data != null){
			urlParameters.add(new BasicNameValuePair("data", data));
		}
		if(!this.set_tags.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tags", this.set_tags));
		}
		if(!this.set_tweet.isEmpty()){
			urlParameters.add(new BasicNameValuePair("tweet", this.set_tweet));
		}
		if(!this.set_date.isEmpty()){
			urlParameters.add(new BasicNameValuePair("date", this.set_date));
		}
		if(!this.set_format.isEmpty()){
			urlParameters.add(new BasicNameValuePair("format", this.set_format));
		}
		if(!this.set_slug.isEmpty()){
			urlParameters.add(new BasicNameValuePair("slug", this.set_slug));
		}
		if(!this.set_state.isEmpty()){
			urlParameters.add(new BasicNameValuePair("state", this.set_state));
		}
		
		//Add the post vars to the request
		try {
			get.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
