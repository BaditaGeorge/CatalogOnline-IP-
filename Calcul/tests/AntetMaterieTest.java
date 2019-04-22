import org.junit.Test;

import static org.junit.Assert.*;

public class AntetMaterieTest {



    @Test
    public void verificareAntetMaterie() {

        // la inceput am pus o lista valida de campuri
        String[] campuri = {"L1", "L2", "E"};
        AntetMaterie MockAntet = new AntetMaterie(campuri);
        MockAntet.verificareAntetMaterie(); // aici se apeleaza functia pe care o scrii tu, in care pentru vectorul de mai sus ar trebui sa obtii in mesajPentruFront "Antetul este valid"
        assertEquals("Antetul este valid", MockAntet.mesajPentruFront);


        //acum in vector voi avea doua variabele L2
        campuri[0] = "L2";
        MockAntet.setCampuriAntet(campuri);
        MockAntet.verificareAntetMaterie(); // acum functia ar trebui sa puna in mesajPentruFront eroarea de mai jos
        assertEquals("Eroare: Nu e permisa repetarea campurilor", MockAntet.mesajPentruFront);

        //una dintre variabilele din vector va fi sum
        campuri[0] = "sum";
        MockAntet.setCampuriAntet(campuri);
        MockAntet.verificareAntetMaterie(); // in mesajFront ar trebui sa ai eroarea de mai jos
        assertEquals("Eroare: Cuvintele sum, min si max sunt rezervate pentru functii", MockAntet.mesajPentruFront);

        campuri[0] = "1L";
        MockAntet.setCampuriAntet(campuri);
        MockAntet.verificareAntetMaterie(); // in mesajFront ar trebui sa ai eroarea de mai jos
        assertEquals("Eroare: Campurile din antet pot contine doar litere si cifre si trebuie sa inceapa cu o litera", MockAntet.mesajPentruFront);
    }

}