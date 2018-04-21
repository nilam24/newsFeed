package in.cdac.newsfeedapp;

import android.graphics.Bitmap;


/**
 * Created by Dell1 on 26/01/2018.
 */

public class NewsPojo {

    private String webTitle;
    private String sectionName;
    private String webPublicationDate;
    private String webUrl;
    private String firstName;
    private String lastName;
    private Bitmap bylineImageUrl;

    public NewsPojo() {
    }

    public NewsPojo(String webTitle, String sectionName, String webPublicationDate, String webUrl, String firstName, String lastName, Bitmap bylineImageUrl) {

        this.webTitle = webTitle;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bylineImageUrl = bylineImageUrl;

    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {

        this.webPublicationDate = webPublicationDate;

    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getBylineImageUrl() {
        return bylineImageUrl;
    }

    public void setBylineImageUrl(Bitmap bylineImageUrl) {
        this.bylineImageUrl = bylineImageUrl;
    }

}
