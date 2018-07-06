package android.example.com.gymapp;

public class SessionItem {

    private String exercise;
    private int sets;
    private String gym;
    private String instructor;

    public SessionItem(String mexercise, int msets, String mgym, String minstructor){

        this.exercise = mexercise;
        this.sets = msets;
        this.gym = mgym;
        this.instructor = minstructor;

    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
