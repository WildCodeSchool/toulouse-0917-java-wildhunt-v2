package fr.indianacroft.wildhunt;

public class QuestModel {

    abstract class Status {
        private final static String TODO = "TODO";
        private final static String DONE = "DONE";
        private final static String DROPPED = "DROPPED";
    }

    abstract class Difficulty {
        private final static String EASY = "EASY";
        private final static String MEDIUM = "MEDIUM";
        private final static String HARD = "HARD";
    }

    private String key;
    private String name;
    private String description;
    private String hint;
    private String photoUrl;
    private String address;
    private long latitude;
    private long longitude;
    private String difficulty;
    private int order;
    private String status;

    public QuestModel() {
    }

    /**
     *
     * @param key QuestModel id
     * @param name QuestModel name
     * @param description QuestModel description
     * @param hint QuestModel hint
     * @param photoUrl QuestModel photoUrl from GoogleMaps
     * @param address QuestModel hint
     * @param latitude QuestModel latitude from GoogleMaps
     * @param longitude QuestModel longitude from GoogleMaps
     * @param difficulty QuestModel difficulty {EASY, MEDIUM, HARD}
     * @param order QuestModel order in ExpeditionModel quests
     */
    public QuestModel(String key, String name, String description, String hint, String photoUrl, String address, long latitude, long longitude, String difficulty, int order) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.hint = hint;
        this.photoUrl = photoUrl;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.difficulty = difficulty;
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
