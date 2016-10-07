package me.boops.boops_jumblr;

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

public class BlogLikes {

	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	//Optional call settings

	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;
	
	//Define private tumblr vars
	private int tumblr_like_count;
	
	//Define Liked post tumblr strings
	private String tumblr_like_date;
	private boolean tumblr_display_avatar;
	private Long tumblr_like_timestamp;
	private String tumblr_captation;
	private int tumblr_liked_post_list_size;
	
	//Return HTTP code
	public int getHTTPCode(){
		return this.httprescode;
	}
	
	//Return JSON Dump
	public String getJSON(){
		return this.json_dump;
	}

	// Master call that sets the needed api keys
	public BlogLikes(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}

	public void getLikes(String blog) {

		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);

		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/likes";
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
