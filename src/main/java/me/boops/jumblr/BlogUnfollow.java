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

public class BlogUnfollow {
	
	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;
	
	// Define private HTTP strings
	private int httprescode;

	// Define JSON dump
	private String json_dump;
	
	// Return HTTP code
	public int getHTTPCode() {
		return this.httprescode;
	}

	// Return JSON Dump
	public String getJSON() {
		return this.json_dump;
	}
	
	// Master call that sets the needed api keys
	public BlogUnfollow(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}
	
	public void unfollow(String blog){
		
		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);
		
		//Build The URL
		String url = "https://api.tumblr.com/v2/user/unfollow";
		
		// Setup The Request
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(10*1000).setConnectTimeout(10*1000).setConnectionRequestTimeout(10*1000).build();
		HttpPost get = new HttpPost(url);
		get.setConfig(reqConfig);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();;
		urlParameters.add(new BasicNameValuePair("url", blog));
		
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

				// Now parse the res
				String meta = new BasicResponseHandler().handleResponse(res);
				JSONObject json = new JSONObject(meta);

				// Dump Json
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
