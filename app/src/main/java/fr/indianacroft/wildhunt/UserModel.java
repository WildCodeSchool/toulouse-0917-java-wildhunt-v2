package fr.indianacroft.wildhunt;

import java.util.Map;

public class UserModel {

    private String key;
    private String name;
    private String photoUrl;
    private Map<String, ExpeditionModel> created = null;
    private Map<String, ExpeditionModel> quested = null;

    public UserModel() {
    }

    /**
     *
     * @param key user id from GoogleSignInAccount
     * @param name user name from GoogleSignInAccount
     * @param photoUrl user photoUrl from GoogleSignInAccount
     */
    public UserModel(String key, String name, String photoUrl) {
        this.key = key;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    /**
     *
     * @param key user id from GoogleSignInAccount
     * @param name user name from GoogleSignInAccount
     * @param photoUrl user photoUrl from GoogleSignInAccount
     * @param created list of ExpeditionModel created by the user
     * @param quested list of ExpeditionModel quested by the user
     */
    public UserModel(String key, String name, String photoUrl, Map<String, ExpeditionModel> created, Map<String, ExpeditionModel> quested) {
        this.key = key;
        this.name = name;
        this.photoUrl = photoUrl;
        this.created = created;
        this.quested = quested;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Map<String, ExpeditionModel> getCreated() {
        return created;
    }

    public void setCreated(Map<String, ExpeditionModel> created) {
        this.created = created;
    }

    public Map<String, ExpeditionModel> getQuested() {
        return quested;
    }

    public void setQuested(Map<String, ExpeditionModel> quested) {
        this.quested = quested;
    }
}
