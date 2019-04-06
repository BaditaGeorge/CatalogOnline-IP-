import org.junit.Test;
import static org.junit.Assert.*;

public class AntetMaterieTest {

    @Test
    public void AntetMaterie()
    {
        String[] campuri = {"L1", "L2", "E"};

        AntetMaterie MockAntet = new AntetMaterie(campuri);
        assertEquals(3,MockAntet.nrCampuri);
        assertEquals("L1",MockAntet.CampuriAntet[0]);
        assertEquals("L2",MockAntet.CampuriAntet[1]);
        assertEquals("E",MockAntet.CampuriAntet[2]);

    }

    @Test
    public void verificareAntetMaterie()
    {
        String[] campuri = {"L1", "L2", "E"};
        AntetMaterie MockAntet = new AntetMaterie(campuri);
        assertEquals("Antetul este valid", MockAntet.mesajPentruFront);

        campuri[0]="L2";
        MockAntet.setCampuriAntet(campuri);
        assertEquals("Eroare: Nu e permisa repetarea campurilor", MockAntet.mesajPentruFront);

        campuri[0]="sum";
        MockAntet.setCampuriAntet(campuri);
        assertEquals("Eroare: Cuvantul sum este rezervat pentru o functie", MockAntet.mesajPentruFront);

    }

}