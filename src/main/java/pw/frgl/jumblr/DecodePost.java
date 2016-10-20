package pw.frgl.jumblr;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DecodePost {

	// Private Tumblr Strings
	private String tumblr_date;
	private boolean tumblr_display_avatar = false;
	private String tumblr_caption;
	private String tumblr_type;
	private boolean tumblr_liked;
	private String tumblr_source_url;
	private String tumblr_source_url_short;
	private boolean tumblr_can_like;
	private String tumblr_image_paramlink;
	private long tumblr_id;
	private String tumblr_state;
	private String tumblr_slug;
	private int tumblr_note_count;
	private long tumblr_timestamp;
	private String tumblr_summary;
	private List<String> tumblr_highlighted = new ArrayList<String>();
	private String tumblr_format;
	private boolean tumblr_followed;
	private List<String> tumblr_tags = new ArrayList<String>();
	private String tumblr_source_title;
	private boolean tumblr_can_reblog;
	private boolean tumblr_can_reply;
	private String tumblr_rebloging_post_url;
	private String tumblr_blog_name;
	private boolean tumblr_can_send_message;
	private String tumblr_reblog_key;

	// Reblog Sub strings
	private String tumblr_reblog_comment;
	private String tumblr_reblog_tree;

	// Trail private Strings
	private String tumblr_trail_content_raw;
	private boolean tumblr_trail_is_root;
	private long tumblr_trail_post_id;
	private String tumblr_trail_content;

	// Blog trail private Strings
	private boolean tumblr_trail_blog_share_likes;
	private String tumblr_trail_blog_name;
	private boolean tumblr_trail_blog_can_follow;
	private boolean tumblr_trail_blog_active;
	private boolean tumblr_trail_blog_share_following;

	// Blog trail theme
	private int tumblr_trail_blog_theme_header_focus_height;
	private String tumblr_trail_blog_theme_header_image;
	private String tumblr_trail_blog_theme_header_image_scaled;
	private boolean tumblr_trail_blog_theme_show_desc;
	private String tumblr_trail_blog_theme_body_font;
	private String tumblr_trail_blog_theme_title_color;
	private int tumblr_trail_blog_theme_header_focus_width;
	private String tumblr_trail_blog_theme_header_bounds;
	private int tumblr_trail_blog_theme_full_width;
	private String tumblr_trail_blog_theme_title_font;
	private String tumblr_trail_blog_theme_avatar_shape;
	private String tumblr_trail_blog_theme_font_weight;
	private String tumblr_trail_blog_theme_link_color;
	private String tumblr_trail_blog_theme_background_color;
	private boolean tumblr_trail_blog_theme_show_title;
	private boolean tumblr_trail_blog_theme_show_avatar;
	private int tumblr_trail_blog_theme_header_full_height;
	private boolean tumblr_trail_blog_theme_header_strech;
	private String tumblr_trail_blog_theme_header_image_focused;
	private boolean tumblr_trail_blog_theme_show_header_image;

	// Private Tumblr Photos String
	private String tumblr_photo_caption;
	private int tumblr_photo_org_width;
	private String tumblr_photo_org_url;
	private int tumblr_photo_org_height;
	private List<Integer> tumblr_photo_alt_widths = new ArrayList<Integer>();
	private List<Integer> tumblr_photo_alt_heights = new ArrayList<Integer>();
	private List<String> tumblr_photo_alt_urls = new ArrayList<String>();
	private int tumblr_alt_count;

	// Private Tumblr text Strings
	private String tumblr_text_title;
	private String tumblr_text_body;

	// Private Tumblr Quote vars
	private String tumblr_quote_source;
	private String tumblr_quote_text;

	// Private tumblr link vars
	private String tumblr_link_title;
	private String tumblr_link_url;
	private String tumblr_link_publisher;
	private String tumblr_link_excerpt;

	// Private tumblr Chat vars
	private String tumblr_chat_title;
	private String tumblr_chat_body;
	private int tumblr_chat_dialogue_length;
	private List<String> tumblr_chat_dialogue_phrase = new ArrayList<String>();
	private List<String> tumblr_chat_dialogue_name = new ArrayList<String>();
	private List<String> tumblr_chat_dialogue_label = new ArrayList<String>();

	// Private Tumblr audio vars
	private String tumblr_audio_source;
	private String tumblr_audio_embed;
	private String tumblr_audio_player;
	private int tumblr_audio_plays;
	private String tumblr_audio_url;

	// Return tumblr strings
	public String getPostDate() {
		return this.tumblr_date;
	}

	public boolean shouldPostDisplayAvatar() {
		return this.tumblr_display_avatar;
	}

	public String getpostCaption() {
		return this.tumblr_caption;
	}

	public String getPostType() {
		return this.tumblr_type;
	}

	public boolean isPostLiked() {
		return this.tumblr_liked;
	}

	public String getSourceURL() {
		return this.tumblr_source_url;
	}

	public String getShortSourceURL() {
		return this.tumblr_source_url_short;
	}

	public boolean canLikePost() {
		return this.tumblr_can_like;
	}

	public String getImagePermalink() {
		return this.tumblr_image_paramlink;
	}

	public long getPostID() {
		return this.tumblr_id;
	}

	public String getPostState() {
		return this.tumblr_state;
	}

	public String getPostSlug() {
		return this.tumblr_slug;
	}

	public int getNoteCount() {
		return this.tumblr_note_count;
	}

	public long getTimeStamp() {
		return this.tumblr_timestamp;
	}

	public String getSummary() {
		return this.tumblr_summary;
	}

	public List<String> getHighlighted() {
		return this.tumblr_highlighted;
	}

	public String getFormat() {
		return this.tumblr_format;
	}

	public boolean isFollowed() {
		return this.tumblr_followed;
	}

	public List<String> getPostTags() {
		return this.tumblr_tags;
	}

	public String getSourceTitle() {
		return this.tumblr_source_title;
	}

	public boolean canReblog() {
		return this.tumblr_can_reblog;
	}

	public boolean canReply() {
		return this.tumblr_can_reply;
	}

	public String getRebloggingURL() {
		return this.tumblr_rebloging_post_url;
	}

	public String getBlogName() {
		return this.tumblr_blog_name;
	}

	public boolean canSendMessage() {
		return this.tumblr_can_send_message;
	}

	public String getReblogKey() {
		return this.tumblr_reblog_key;
	}

	// Return Reblog Sub String
	public String getReblogComment() {
		return this.tumblr_reblog_comment;
	}

	public String getReblogTree() {
		return this.tumblr_reblog_tree;
	}

	// Return trail private strings
	public String getTrailContentRaw() {
		return this.tumblr_trail_content_raw;
	}

	public boolean isRoot() {
		return this.tumblr_trail_is_root;
	}

	public long getTrailPostID() {
		return this.tumblr_trail_post_id;
	}

	public String getTrailContent() {
		return this.tumblr_trail_content;
	}

	// Return blog trail vars
	public boolean canTrailBlogShareLikes() {
		return this.tumblr_trail_blog_share_likes;
	}

	public String getTrailBlogName() {
		return this.tumblr_trail_blog_name;
	}

	public boolean canFollowTrailBlog() {
		return this.tumblr_trail_blog_can_follow;
	}

	public boolean isTrailBlogActive() {
		return this.tumblr_trail_blog_active;
	}

	public boolean canShareTrailBlogFollowing() {
		return this.tumblr_trail_blog_share_following;
	}

	// Return trail blog theme
	public int getTrailBlogThemeHeaderFocusHeight() {
		return this.tumblr_trail_blog_theme_header_focus_height;
	}

	public String getTrailBlogHeaderImage() {
		return this.tumblr_trail_blog_theme_header_image;
	}

	public String getTrailBlogHeaderImageScaled() {
		return this.tumblr_trail_blog_theme_header_image_scaled;
	}

	public boolean shouldShowTrailBlocDesc() {
		return this.tumblr_trail_blog_theme_show_desc;
	}

	public String getTrailBlogBodyFont() {
		return this.tumblr_trail_blog_theme_body_font;
	}

	public String getTrailBlogTitleColor() {
		return this.tumblr_trail_blog_theme_title_color;
	}

	public int getTrailBlogHeaderFocusWidth() {
		return this.tumblr_trail_blog_theme_header_focus_width;
	}

	public String getTrailBlogHeaderBounds() {
		return this.tumblr_trail_blog_theme_header_bounds;
	}

	public int getTrailBlogFullWidth() {
		return this.tumblr_trail_blog_theme_full_width;
	}

	public String getTrailBlogThemeTitleFont() {
		return this.tumblr_trail_blog_theme_title_font;
	}

	public String getTrailBlogAvatarShape() {
		return this.tumblr_trail_blog_theme_avatar_shape;
	}

	public String getTrailBlogFontWeight() {
		return this.tumblr_trail_blog_theme_font_weight;
	}

	public String getTrailBlogLinkColor() {
		return this.tumblr_trail_blog_theme_link_color;
	}

	public String getTrailBlogBackgroundColor() {
		return this.tumblr_trail_blog_theme_background_color;
	}

	public boolean shouldShowTrailBlogTitle() {
		return this.tumblr_trail_blog_theme_show_title;
	}

	public boolean shouldShowTrailBlogAvatar() {
		return this.tumblr_trail_blog_theme_show_avatar;
	}

	public int getTrailBlogFullHeaderHeight() {
		return this.tumblr_trail_blog_theme_header_full_height;
	}

	public boolean showStrechTrailBlogHeader() {
		return this.tumblr_trail_blog_theme_header_strech;
	}

	public String getTrailBlogHeaderFocused() {
		return this.tumblr_trail_blog_theme_header_image_focused;
	}

	public boolean shouldShowTrailBlogHeaderImage() {
		return this.tumblr_trail_blog_theme_show_header_image;
	}

	// Tumblr Photos vars
	public String getPhotoCaption() {
		return this.tumblr_photo_caption;
	}

	public int getOrginalPhotoWidth() {
		return this.tumblr_photo_org_width;
	}

	public String getOrginalPhotoURL() {
		return this.tumblr_photo_org_url;
	}

	public int getOrginalPhotoHeight() {
		return this.tumblr_photo_org_height;
	}

	public int getAltPhotoWidth(int photo) {
		return this.tumblr_photo_alt_widths.get(photo);
	}

	public int getAltPhotoHeight(int photo) {
		return this.tumblr_photo_alt_heights.get(photo);
	}

	public String getAltPhotoURL(int photo) {
		return this.tumblr_photo_alt_urls.get(photo);
	}

	public int getAltPhotosCount() {
		return this.tumblr_alt_count;
	}

	// Return Tumblr Text vars
	public String getPostTextTitle() {
		return this.tumblr_text_title;
	}

	public String getPostTextBody() {
		return this.tumblr_text_body;
	}

	// Return tumblr quote vars
	public String getPostQuoteSource() {
		return this.tumblr_quote_source;
	}

	public String getPostQuoteText() {
		return this.tumblr_quote_text;
	}

	// Retirn Tumblr Link vars
	public String getPostLinkTitle() {
		return this.tumblr_link_title;
	}

	public String getPostLinkURL() {
		return this.tumblr_link_url;
	}

	public String getPostLinkPublisher() {
		return this.tumblr_link_publisher;
	}

	public String getPostLinkExcerpt() {
		return this.tumblr_link_excerpt;
	}

	// Return tumblr chat vars
	public String getChatTitle() {
		return this.tumblr_chat_title;
	}

	public String getChatBody() {
		return this.tumblr_chat_body;
	}

	public int getDialogueLength() {
		return this.tumblr_chat_dialogue_length;
	}

	public String getDialoguePhrase(int i) {
		return this.tumblr_chat_dialogue_phrase.get(i);
	}

	public String getDialogueName(int i) {
		return this.tumblr_chat_dialogue_name.get(i);
	}

	public String getDialogueLabel(int i) {
		return this.tumblr_chat_dialogue_label.get(i);
	}

	// Return tumblr audio vars
	public String getAudioSource() {
		return this.tumblr_audio_source;
	}

	public String getAudioEmbed() {
		return this.tumblr_audio_embed;
	}

	public String getAudioPlayer() {
		return this.tumblr_audio_player;
	}

	public int getAudioPlays() {
		return this.tumblr_audio_plays;
	}

	public String getAudioURL() {
		return this.tumblr_audio_url;
	}

	// Decode the Post JSON
	public void decode(String post) {

		// Clear The lists
		this.tumblr_highlighted.clear();
		this.tumblr_tags.clear();

		// Convert The Stringed Post To A JSON Object
		// Check for null
		if (post != null) {

			JSONObject json = new JSONObject(post);

			// Now Check And See What We Can Set!
			if (json.has("date")) {
				this.tumblr_date = json.getString("date");
			}
			if (json.has("display_avatar")) {
				this.tumblr_display_avatar = json.getBoolean("display_avatar");
			}
			if (json.has("caption")) {
				this.tumblr_caption = json.getString("caption");
			}
			if (json.has("type")) {
				this.tumblr_type = json.getString("type");
			}
			if (json.has("liked")) {
				this.tumblr_liked = json.getBoolean("liked");
			}
			if (json.has("source_url")) {
				this.tumblr_source_url = json.getString("source_url");
			}
			if (json.has("short_url")) {
				this.tumblr_source_url_short = json.getString("short_url");
			}
			if (json.has("can_like")) {
				this.tumblr_can_like = json.getBoolean("can_like");
			}
			if (json.has("image_permalink")) {
				this.tumblr_image_paramlink = json.getString("image_permalink");
			}
			if (json.has("id")) {
				this.tumblr_id = json.getLong("id");
			}
			if (json.has("state")) {
				this.tumblr_state = json.getString("state");
			}
			if (json.has("slug")) {
				this.tumblr_slug = json.getString("slug");
			}
			if (json.has("note_count")) {
				this.tumblr_note_count = json.getInt("note_count");
			}
			if (json.has("timestamp")) {
				this.tumblr_timestamp = json.getLong("timestamp");
			}
			if (json.has("summary")) {
				this.tumblr_summary = json.getString("summary");
			}
			if (json.has("highlighted")) {
				for (int i = 0; json.getJSONArray("highlighted").length() > i; i++) {
					this.tumblr_highlighted.add(json.getJSONArray("highlighted").getString(i));
				}
			}
			if (json.has("format")) {
				this.tumblr_format = json.getString("format");
			}
			if (json.has("followed")) {
				this.tumblr_followed = json.getBoolean("followed");
			}
			if (json.has("tags")) {
				for (int i = 0; json.getJSONArray("tags").length() > i; i++) {
					this.tumblr_tags.add(json.getJSONArray("tags").getString(i));
				}
			}
			if (json.has("source_title")) {
				this.tumblr_source_title = json.getString("source_title");
			}
			if (json.has("can_reblog")) {
				this.tumblr_can_reblog = json.getBoolean("can_reblog");
			}
			if (json.has("can_reply")) {
				this.tumblr_can_reply = json.getBoolean("can_reply");
			}
			if (json.has("post_url")) {
				this.tumblr_rebloging_post_url = json.getString("post_url");
			}
			if (json.has("blog_name")) {
				this.tumblr_blog_name = json.getString("blog_name");
			}
			if (json.has("can_send_in_message")) {
				this.tumblr_can_send_message = json.getBoolean("can_send_in_message");
			}
			if (json.has("reblog_key")) {
				this.tumblr_reblog_key = json.getString("reblog_key");
			}

			// Decode the reblog sub
			if (json.has("reblog")) {
				this.tumblr_reblog_comment = json.getJSONObject("reblog").getString("comment");
				this.tumblr_reblog_tree = json.getJSONObject("reblog").getString("tree_html");
			}

			// Decode Trail vars
			if (json.has("trail")) {
				JSONArray trail = json.getJSONArray("trail");
				if(!trail.isNull(0)){
					JSONObject trail_lower = trail.getJSONObject(0);
					if (trail_lower.has("content_raw")) {
						this.tumblr_trail_content_raw = trail_lower.getString("content_raw");
					}
					if (trail_lower.has("is_root_item")) {
						this.tumblr_trail_is_root = trail_lower.getBoolean("is_root_item");
					}
					if (trail_lower.has("post")) {
						this.tumblr_trail_post_id = trail_lower.getJSONObject("post").getLong("id");
					}
					if (trail_lower.has("content")) {
						this.tumblr_trail_content = trail_lower.getString("content");
					}
					
					// Set Blog Strings
					if(trail_lower.has("blog")){
						JSONObject blog = trail_lower.getJSONObject("blog");
						if (blog.has("share_likes")) {
							this.tumblr_trail_blog_share_likes = blog.getBoolean("share_likes");
						}
						if (blog.has("name")) {
							this.tumblr_trail_blog_name = blog.getString("name");
						}
						if (blog.has("can_be_followed")) {
							this.tumblr_trail_blog_can_follow = blog.getBoolean("can_be_followed");
						}
						if (blog.has("active")) {
							this.tumblr_trail_blog_active = blog.getBoolean("active");
						}
						if (blog.has("share_following")) {
							this.tumblr_trail_blog_share_following = blog.getBoolean("share_following");
						}
					}
					
					if(trail_lower.has("blog")){
						JSONObject blog = trail_lower.getJSONObject("blog");
						if(blog.has("theme")){
							// Set blog theme vars
							try{
								JSONObject theme = blog.getJSONObject("theme");
								
								if (theme.has("header_focus_height")) {
									this.tumblr_trail_blog_theme_header_focus_height = theme.getInt("header_focus_height");
								}
								if (theme.has("header_image")) {
									this.tumblr_trail_blog_theme_header_image = theme.getString("header_image");
								}
								if (theme.has("header_image_scaled")) {
									this.tumblr_trail_blog_theme_header_image_focused = theme.getString("header_image_scaled");
								}
								if (theme.has("show_description")) {
									this.tumblr_trail_blog_theme_show_desc = theme.getBoolean("show_description");
								}
								if (theme.has("body_font")) {
									this.tumblr_trail_blog_theme_body_font = theme.getString("body_font");
								}
								if (theme.has("title_color")) {
									this.tumblr_trail_blog_theme_title_color = theme.getString("title_color");
								}
								if (theme.has("header_focus_width")) {
									this.tumblr_trail_blog_theme_header_focus_width = theme.getInt("header_focus_width");
								}
								if (theme.has("header_bounds")) {
									this.tumblr_trail_blog_theme_header_bounds = theme.get("header_bounds").toString();
								}
								if (theme.has("header_full_width")) {
									this.tumblr_trail_blog_theme_full_width = theme.getInt("header_full_width");
								}
								if (theme.has("title_font")) {
									this.tumblr_trail_blog_theme_title_font = theme.getString("title_font");
								}
								if (theme.has("avatar_shape")) {
									this.tumblr_trail_blog_theme_avatar_shape = theme.getString("avatar_shape");
								}
								if (theme.has("title_font_weight")) {
									this.tumblr_trail_blog_theme_font_weight = theme.getString("title_font_weight");
								}
								if (theme.has("link_color")) {
									this.tumblr_trail_blog_theme_link_color = theme.getString("link_color");
								}
								if (theme.has("background_color")) {
									this.tumblr_trail_blog_theme_background_color = theme.getString("background_color");
								}
								if (theme.has("show_title")) {
									this.tumblr_trail_blog_theme_show_title = theme.getBoolean("show_title");
								}
								if (theme.has("show_avatar")) {
									this.tumblr_trail_blog_theme_show_avatar = theme.getBoolean("show_avatar");
								}
								if (theme.has("header_full_height")) {
									this.tumblr_trail_blog_theme_header_full_height = theme.getInt("header_full_height");
								}
								if (theme.has("header_stretch")) {
									this.tumblr_trail_blog_theme_header_strech = theme.getBoolean("header_stretch");
								}
								if (theme.has("header_image_focused")) {
									this.tumblr_trail_blog_theme_header_image_focused = theme.getString("header_image_focused");
								}
								if (theme.has("show_header_image")) {
									this.tumblr_trail_blog_theme_show_header_image = theme.getBoolean("show_header_image");
								}
							} catch(JSONException e){
								
							}
						}
					}
					
				}
			}

			// Decode The Photos Feild
			if (this.tumblr_type.toLowerCase().equals("photo")) {
				this.tumblr_photo_caption = json.getJSONArray("photos").getJSONObject(0).getString("caption");
				this.tumblr_photo_org_width = json.getJSONArray("photos").getJSONObject(0)
						.getJSONObject("original_size").getInt("width");
				this.tumblr_photo_org_height = json.getJSONArray("photos").getJSONObject(0)
						.getJSONObject("original_size").getInt("height");
				this.tumblr_photo_org_url = json.getJSONArray("photos").getJSONObject(0).getJSONObject("original_size")
						.getString("url");
				this.tumblr_alt_count = json.getJSONArray("photos").getJSONObject(0).getJSONArray("alt_sizes").length();

				// Now Create A List Of Alt Sizes
				for (int i = 0; json.getJSONArray("photos").getJSONObject(0).getJSONArray("alt_sizes")
						.length() > i; i++) {
					this.tumblr_photo_alt_widths.add(json.getJSONArray("photos").getJSONObject(0)
							.getJSONArray("alt_sizes").getJSONObject(i).getInt("width"));
					this.tumblr_photo_alt_urls.add(json.getJSONArray("photos").getJSONObject(0)
							.getJSONArray("alt_sizes").getJSONObject(i).getString("url"));
					this.tumblr_photo_alt_heights.add(json.getJSONArray("photos").getJSONObject(0)
							.getJSONArray("alt_sizes").getJSONObject(i).getInt("height"));
				}
			}

			// Decode If Text Post
			if (this.tumblr_type.toLowerCase().equals("text")) {
				this.tumblr_text_title = json.getString("title");
				this.tumblr_text_body = json.getString("body");
			}

			// Decode if Quote post
			if (this.tumblr_type.toLowerCase().equals("quote")) {
				this.tumblr_quote_source = json.getString("source");
				this.tumblr_quote_text = json.getString("text");
			}

			// Decode If Link
			if (this.tumblr_type.toLowerCase().equals("link")) {
				this.tumblr_link_title = json.getString("title");
				this.tumblr_link_url = json.getString("url");
				this.tumblr_link_excerpt = json.getString("excerpt");
				this.tumblr_link_publisher = json.getString("publisher");
			}

			// Decode if chat
			if (this.tumblr_type.toLowerCase().equals("chat")) {
				this.tumblr_chat_title = json.getString("title");
				this.tumblr_chat_body = json.getString("body");
				this.tumblr_chat_dialogue_length = json.getJSONArray("dialogue").length();
				for (int i = 0; this.tumblr_chat_dialogue_length > i; i++) {
					this.tumblr_chat_dialogue_phrase
							.add(json.getJSONArray("dialogue").getJSONObject(i).getString("phrase"));
					this.tumblr_chat_dialogue_name
							.add(json.getJSONArray("dialogue").getJSONObject(i).getString("name"));
					this.tumblr_chat_dialogue_label
							.add(json.getJSONArray("dialogue").getJSONObject(i).getString("label"));
				}

			}

			// Decode if audio
			if (this.tumblr_type.toLowerCase().equals("audio")) {
				this.tumblr_audio_url = json.getString("audio_source_url");
				this.tumblr_audio_embed = json.getString("embed");
				this.tumblr_audio_player = json.getString("player");
				this.tumblr_audio_plays = json.getInt("plays");
				this.tumblr_audio_url = json.getString("audio_url");
			}

		}
	}

}
