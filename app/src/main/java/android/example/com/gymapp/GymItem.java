package android.example.com.gymapp;

public class GymItem {
    private String mgymName;
    private int mrating;
    private String mMobile;

    public GymItem(String gymName, int rating, String mobile){
        mgymName = gymName;
        mrating=rating;
        mMobile = mobile;

    }

    public String getGymName() {
        return mgymName;
    }

    public void setGymName(String mgymName) {
        this.mgymName = mgymName;
    }

    public int getRating() {
        return mrating;
    }

    public void setRating(int mrating) {
        this.mrating = mrating;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mMobile) {
        this.mMobile = mMobile;
    }
}
