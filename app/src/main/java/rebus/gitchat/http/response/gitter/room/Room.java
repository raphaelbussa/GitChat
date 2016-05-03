package rebus.gitchat.http.response.gitter.room;

import java.io.Serializable;

import rebus.gitchat.http.response.gitter.user.User;

/**
 * Created by Raphael on 24/12/2015.
 */
public class Room implements Serializable {

    private String id;
    private String name;
    private String topic;
    private String uri;
    private boolean oneToOne;
    private int userCount;
    private int unreadItems;
    private int mentions;
    private String lastAccessTime;
    private boolean activity;
    private String url;
    private String githubType;
    private String security;
    private boolean noindex;
    private String[] tags;
    private boolean roomMember;
    private int v;
    private User user;
    private boolean premium;
    private boolean lurk;
    private int favourite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isOneToOne() {
        return oneToOne;
    }

    public void setOneToOne(boolean oneToOne) {
        this.oneToOne = oneToOne;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getUnreadItems() {
        return unreadItems;
    }

    public void setUnreadItems(int unreadItems) {
        this.unreadItems = unreadItems;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGithubType() {
        return githubType;
    }

    public void setGithubType(String githubType) {
        this.githubType = githubType;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public boolean isNoindex() {
        return noindex;
    }

    public void setNoindex(boolean noindex) {
        this.noindex = noindex;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isRoomMember() {
        return roomMember;
    }

    public void setRoomMember(boolean roomMember) {
        this.roomMember = roomMember;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isLurk() {
        return lurk;
    }

    public void setLurk(boolean lurk) {
        this.lurk = lurk;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
