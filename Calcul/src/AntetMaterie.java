import java.util.Arrays;

public class AntetMaterie {

    public String[] CampuriAntet = new String[50];
    public int nrCampuri;
    public String mesajPentruFront;

    public AntetMaterie(String[] antet) {
        for (int i = 0; i < antet.length; i++) {
            this.CampuriAntet[i] = antet[i];
        }
        nrCampuri = antet.length;
    }

    public void setCampuriAntet(String[] campuriAntet) {
        for (int i = 0; i < campuriAntet.length; i++) {
            this.CampuriAntet[i] = campuriAntet[i];
        }
        nrCampuri = campuriAntet.length;
    }

    public void verificareAntetMaterie() {

    }
}
