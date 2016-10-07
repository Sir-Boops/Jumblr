package me.boops.boops_jumblr;

import java.util.ArrayList;
import java.util.List;

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

public class BlogInfo {

	// Define private oauth strings
	private String cust_key;
	private String cust_sec;
	private String token;
	private String token_sec;

	// Define private HTTP strings
	private int httprescode;
	
	//Define JSON dump
	private String json_dump;
	
	//Define private tumblr response strings
	private String tumblr_msg;
	private String tumblr_blog_title;
	private String tumblr_blog_name;
	private String tumblr_blog_url;
	private String tumblr_blog_desc;
	private String tumblr_ask_page_title;
	private String tumblr_submit_guidelines;
	private String tumblr_submit_title;
	private String tumblr_facebook;
	private String tumblr_tweet;
	private String tumblr_facebook_opengraph;
	private String tumblr_type;
	
	// Define private tumblr booleans
	private boolean tumblr_can_submit;
	private boolean tumblr_can_send_fan_mail;
	private boolean tumblr_see_likes;
	private boolean tumblr_can_sub;
	private boolean tumblr_is_following;
	private boolean tumblr_is_nsfw;
	private boolean tumblr_can_ask;
	private boolean tumblr_can_ask_anon;
	private boolean tumblr_block_by_primary;
	private boolean tumblr_is_subscribed;
	private boolean tumblr_twitter_enabled;
	private boolean tumblr_is_admin;
	private boolean tumblr_twitter_send;
	private boolean tumblr_is_primary;
	
	//Define private Tumblr ints
	private int tumblr_total_posts;
	private int tumblr_posts;
	private int tumblr_likes;
	private int tumblr_reply_conditions;
	private int tumblr_drafts;
	private int tumblr_messages;
	private int tumblr_queue;
	
	//Define private Tumblr Longs
	private Long tumblr_blog_last_active;
	
	//Define private Tumblr lists
	private JSONArray tumblr_submit_accepted_types;
	private JSONArray tumblr_submit_tags;

	// Return HTTP responses
	public int getHTTPCode() {
		return this.httprescode;
	}
	
	//Return JSON dump
	public String getJSON(){
		return this.json_dump;
	}
	
	//Return Tumblr Strings
	public String getTumblrMSG(){
		return this.tumblr_msg;
	}
	
	public String getTitle(){
		return this.tumblr_blog_title;
	}
	
	public String getName(){
		return this.tumblr_blog_name;
	}
	
	public String getBlogURL(){
		return this.tumblr_blog_url;
	}
	
	public String getDesc(){
		return this.tumblr_blog_desc;
	}
	
	public String getAskPageTitle(){
		return this.tumblr_ask_page_title;
	}
	
	public String getSubmitGuideLines(){
		return this.tumblr_submit_guidelines;
	}
	
	public String getSubmitTitle(){
		return this.tumblr_submit_title;
	}
	
	public String getFacebook(){
		return this.tumblr_facebook;
	}
	
	public String getTweet(){
		return this.tumblr_tweet;
	}
	
	public String getFacebookOpenGraph(){
		return this.tumblr_facebook_opengraph;
	}
	
	public String getType(){
		return this.tumblr_type;
	}
	
	//Return Tumblt Booleans
	public boolean isNSFW(){
		return this.tumblr_is_nsfw;
	}
	
	public boolean canAskBlog(){
		return this.tumblr_can_ask;
	}
	
	
	public boolean canAskAnon(){
		return this.tumblr_can_ask_anon;
	}
	
	public boolean canViewLikes(){
		return this.tumblr_see_likes;
	}
	
	public boolean isBlockedFromPrimary(){
		return this.tumblr_block_by_primary;
	}
	
	public boolean canSubmit(){
		return this.tumblr_can_submit;
	}
	
	public boolean canSendFanMail(){
		return this.tumblr_can_send_fan_mail;
	}
	
	public boolean isFollowing(){
		return this.tumblr_is_following;
	}
	
	public boolean isSubscribed(){
		return this.tumblr_is_subscribed;
	}
	
	public boolean isTwitterEnabled(){
		return this.tumblr_twitter_enabled;
	}
	
	public boolean isAdmin(){
		return this.tumblr_is_admin;
	}
	
	public boolean sendTweet(){
		return this.tumblr_twitter_send;
	}
	
	public boolean isPrimary(){
		return this.tumblr_is_primary;
	}
	
	//Return Tumblr Ints
	public int getTotalPosts(){
		return this.tumblr_total_posts;
	}
	
	public int getPosts(){
		return this.tumblr_posts;
	}
	
	public int getLikes(){
		return this.tumblr_likes;
	}
	
	public int getReplyConditions(){
		return this.tumblr_reply_conditions;
	}
	
	public int getDraftCount(){
		return this.tumblr_drafts;
	}
	
	public int getMessageCount(){
		return this.tumblr_messages;
	}
	
	public int getQueueCount(){
		return this.tumblr_queue;
	}
	
	//Return Tumblr Longs
	public Long getLastActive(){
		return this.tumblr_blog_last_active;
	}
	
	//Return Tumblr Lists
	public List<String> getSubmitTypes(){
		List<String> temp_list = new ArrayList<String>();
		if(this.tumblr_can_submit){
			for(int i=0; i<this.tumblr_submit_accepted_types.length(); i++){
				temp_list.add(this.tumblr_submit_accepted_types.getString(i));
			}
		}
		return temp_list;
	}
	
	public List<String> getSubmitTags(){
		List<String> temp_list = new ArrayList<String>();
		if(this.tumblr_can_submit){
			for(int i=0; i<this.tumblr_submit_accepted_types.length(); i++){
				temp_list.add(this.tumblr_submit_tags.getString(i));
			}	
		}
		return temp_list;
	}
	
	//Master call that sets the needed api keys
	public BlogInfo(String cust_key, String cust_sec, String token, String token_sec) {

		// Set the required oauth strings
		this.cust_key = cust_key;
		this.cust_sec = cust_sec;
		this.token = token;
		this.token_sec = token_sec;

	}

	public void getBlog(String blog) {

		// Define oauth
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(this.cust_key, this.cust_sec);
		consumer.setTokenWithSecret(this.token, this.token_sec);

		// Setup The Request
		String url = "https://api.tumblr.com/v2/blog/" + blog + "/info";
		HttpClient client = HttpClients.custom().setSSLHostnameVerifier(new DefaultHostnameVerifier()).build();
		HttpGet get = new HttpGet(url);

		// Sign the reuqest
		try {
			consumer.sign(get);

			// Send the request
			HttpResponse res = client.execute(get);

			// Check the request status code
			if (res.getStatusLine().getStatusCode() == 200) {
				
				//Was able to complete the request
				this.httprescode = res.getStatusLine().getStatusCode();
				
				//Now parse the res
				String meta = new BasicResponseHandler().handleResponse(res);
				JSONObject json = new JSONObject(meta);
				
				//Dump Json
				this.json_dump = json.toString();
				
				//Check if this is our own blog or not
				if(json.getJSONObject("response").getJSONObject("blog").has("admin")){
					
					//We are an admin of the blog
					//Set booleans into their private cache
					this.tumblr_twitter_enabled = json.getJSONObject("response").getJSONObject("blog").getBoolean("twitter_enabled");
					this.tumblr_is_admin = json.getJSONObject("response").getJSONObject("blog").getBoolean("admin");
					this.tumblr_twitter_send = json.getJSONObject("response").getJSONObject("blog").getBoolean("twitter_send");
					this.tumblr_is_primary = json.getJSONObject("response").getJSONObject("blog").getBoolean("primary");
					
					//Set strings into their private cache
					this.tumblr_facebook = json.getJSONObject("response").getJSONObject("blog").getString("facebook");
					this.tumblr_tweet = json.getJSONObject("response").getJSONObject("blog").getString("tweet");
					this.tumblr_facebook_opengraph = json.getJSONObject("response").getJSONObject("blog").getString("facebook_opengraph_enabled");
					this.tumblr_type = json.getJSONObject("response").getJSONObject("blog").getString("type");
					
					//Set ints into their private cache
					this.tumblr_reply_conditions = json.getJSONObject("response").getJSONObject("blog").getInt("reply_conditions");
					this.tumblr_drafts = json.getJSONObject("response").getJSONObject("blog").getInt("drafts");
					this.tumblr_messages = json.getJSONObject("response").getJSONObject("blog").getInt("messages");
					this.tumblr_queue = json.getJSONObject("response").getJSONObject("blog").getInt("queue");
					
				}
				
				//Set booleans into their private cache
				this.tumblr_can_send_fan_mail = json.getJSONObject("response").getJSONObject("blog").getBoolean("can_send_fan_mail");
				this.tumblr_can_sub = json.getJSONObject("response").getJSONObject("blog").getBoolean("can_subscribe");
				this.tumblr_is_following = json.getJSONObject("response").getJSONObject("blog").getBoolean("followed");
				this.tumblr_is_nsfw = json.getJSONObject("response").getJSONObject("blog").getBoolean("is_nsfw");
				this.tumblr_can_ask = json.getJSONObject("response").getJSONObject("blog").getBoolean("ask");
				this.tumblr_see_likes = json.getJSONObject("response").getJSONObject("blog").getBoolean("share_likes");
				this.tumblr_block_by_primary = this.tumblr_see_likes = json.getJSONObject("response").getJSONObject("blog").getBoolean("is_blocked_from_primary");
				if(this.tumblr_can_ask){
					this.tumblr_can_ask_anon = json.getJSONObject("response").getJSONObject("blog").getBoolean("ask_anon");
				}
				if(this.tumblr_can_sub){
					this.tumblr_is_subscribed = json.getJSONObject("response").getJSONObject("blog").getBoolean("subscribed");
				}
				if(json.getJSONObject("response").getJSONObject("blog").has("can_submit")){
					this.tumblr_can_submit = json.getJSONObject("response").getJSONObject("blog").getBoolean("can_submit");
				}
				
				//Set the strings into their private cache
				this.tumblr_msg = json.getJSONObject("meta").getString("msg");
				this.tumblr_blog_title = json.getJSONObject("response").getJSONObject("blog").getString("title");
				this.tumblr_blog_name = json.getJSONObject("response").getJSONObject("blog").getString("name");
				this.tumblr_blog_url = json.getJSONObject("response").getJSONObject("blog").getString("url");
				this.tumblr_blog_desc = json.getJSONObject("response").getJSONObject("blog").getString("description");
				this.tumblr_ask_page_title = json.getJSONObject("response").getJSONObject("blog").getString("ask_page_title");
				if(this.tumblr_can_submit){
					this.tumblr_submit_guidelines = json.getJSONObject("response").getJSONObject("blog").getJSONObject("submission_terms").getString("guidelines");
					this.tumblr_submit_title = json.getJSONObject("response").getJSONObject("blog").getJSONObject("submission_terms").getString("title");
				}
				
				//Set ints into their private cache
				this.tumblr_total_posts = json.getJSONObject("response").getJSONObject("blog").getInt("total_posts");
				this.tumblr_posts = json.getJSONObject("response").getJSONObject("blog").getInt("posts");
				if(json.getJSONObject("response").getJSONObject("blog").toString().replace("share_likes", "").contains("likes:")){
					this.tumblr_likes = json.getJSONObject("response").getJSONObject("blog").getInt("likes");
				}
				
				//Set Longs into their private cache
				this.tumblr_blog_last_active = json.getJSONObject("response").getJSONObject("blog").getLong("updated");
				
				//Set lists into their place
				if(this.tumblr_can_submit){
					this.tumblr_submit_accepted_types = json.getJSONObject("response").getJSONObject("blog").getJSONObject("submission_terms").getJSONArray("accepted_types");
					this.tumblr_submit_tags = json.getJSONObject("response").getJSONObject("blog").getJSONObject("submission_terms").getJSONArray("tags");
				}
				

			} else {

				// Was not able to complete request
				this.httprescode = res.getStatusLine().getStatusCode();

			}

		} catch (ClientProtocolException e) {
			if(e.getMessage().toLowerCase() == "not found"){
				this.httprescode = 404;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
