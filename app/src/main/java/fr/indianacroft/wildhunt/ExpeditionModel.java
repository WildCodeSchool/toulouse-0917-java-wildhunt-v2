package fr.indianacroft.wildhunt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExpeditionModel {

    abstract class Status {
        private final static String DOING = "DOING";
        private final static String DONE = "DONE";
        private final static String DROPPED = "DROPPED";
    }

    private String key;
    private Map <String, UserModel> user;
    private String name;
    private String description;
    private String status;
    private long date;
    private Map<String, QuestModel> quests = null;

    public ExpeditionModel() {
    }

    /**
     *
     * @param key expedition id
     * @param name expedition name
     * @param description expedition description
     */
    public ExpeditionModel(String key, String name, String description) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.date = new Date().getTime();
    }

    public void addUser(UserModel user) {
        Map<String, UserModel> userMap = new HashMap<>();
        userMap.put(user.getKey(), user);
        this.user = userMap;
    }

    public String getUserKey() {
        for (String key : user.keySet()) {
            return key;
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, UserModel> getUser() {
        return user;
    }

    public void setUser(Map<String, UserModel> user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Map<String, QuestModel> getQuests() {
        return quests;
    }

    public void setQuests(Map<String, QuestModel> quests) {
        this.quests = quests;
    }
}
