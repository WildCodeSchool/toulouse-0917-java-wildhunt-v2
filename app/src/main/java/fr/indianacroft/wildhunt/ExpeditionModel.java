package fr.indianacroft.wildhunt;

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
    private double difficulty;
    private String status;
    private String date;
    private Map<String, QuestModel> quests = null;

    public ExpeditionModel() {
    }

    /**
     *
     * @param key expedition id
     * @param user user model
     * @param name expedition name
     * @param difficulty expedition difficulty
     * @param status expedition status {DOING, DONE, DROPPED}
     * @param date expedition creation date
     */
    public ExpeditionModel(String key, Map<String, UserModel> user, String name, double difficulty, String status, String date) {
        this.key = key;
        this.user = user;
        this.name = name;
        this.difficulty = difficulty;
        this.status = status;
        this.date = date;
    }

    public ExpeditionModel(String key, Map<String, UserModel> user, String name, double difficulty, String status, String date, Map<String, QuestModel> quests) {
        this.key = key;
        this.user = user;
        this.name = name;
        this.difficulty = difficulty;
        this.status = status;
        this.date = date;
        this.quests = quests;
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

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, QuestModel> getQuests() {
        return quests;
    }

    public void setQuests(Map<String, QuestModel> quests) {
        this.quests = quests;
    }
}
