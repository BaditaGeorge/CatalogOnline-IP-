package com.example.demo1;
import java.lang.reflect.Array;
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

        for(int i = 0; i< nrCampuri; i++)
        {

            if((CampuriAntet[i].charAt(0)<'a' || CampuriAntet[i].charAt(0)>'z') &&(CampuriAntet[i].charAt(0)<'A' || CampuriAntet[i].charAt(0)>'Z'))
            {
                mesajPentruFront="Eroare: Campurile din antet pot contine doar litere si cifre si trebuie sa inceapa cu o litera";
                return;
            }

            int j=0;
            while(j<CampuriAntet[i].length() && ((CampuriAntet[i].charAt(j)>='a' && CampuriAntet[i].charAt(j)<='z') ||(CampuriAntet[i].charAt(j)>='A' && CampuriAntet[i].charAt(j)<='Z')))
                j++;
            while(j<CampuriAntet[i].length() &&(CampuriAntet[i].charAt(j)>='0' && CampuriAntet[i].charAt(j)<='9'))
                j++;

            if(j!=CampuriAntet[i].length())
            {
                mesajPentruFront="Eroare: Campurile din antet pot contine doar litere si cifre si trebuie sa inceapa cu o litera";
                return;
            }



            for( j=i+1; j<nrCampuri; j++)
            {
                if(CampuriAntet[j].equals(CampuriAntet[i]))
                {
                    mesajPentruFront="Eroare: Nu e permisa repetarea campurilor";
                    return;
                }
            }

            if(CampuriAntet[i].equals("sum") || CampuriAntet[i].equals("max") || CampuriAntet[i].equals("min"))
            {
                mesajPentruFront="Eroare: Cuvintele sum, min si max sunt rezervate pentru functii";
                return;
            }


        }

        mesajPentruFront="Antetul este valid";
    }

    public String getMesajPentruFront(){
        return mesajPentruFront;
    }
}
