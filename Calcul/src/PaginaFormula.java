import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class PaginaFormula {

    AntetMaterie antet;
    String id_materie;
    String mesajPentruFront;
    private PrelucrareDate prelucrareDate;

    public PaginaFormula(PrelucrareDate prelucrareDate) {
        this.prelucrareDate = prelucrareDate;
    }

    String parsareFormule(String id_materie, String formule) {

        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }
        antet = antet.replaceAll("\\s+", " ");
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);

        String[] list = formule.split(";");
        for (String s : list) {
            Formula formula = new Formula(s);
            formula.parsareFormula();
            mesajPentruFront = formula.getMesajPentruFront();
            if (!mesajPentruFront.equals("Formula este valida"))
                return mesajPentruFront;
            else {
                formula.verificareVariabileFormula(antetMaterie);
                mesajPentruFront = formula.getMesajPentruFront();
                if (!mesajPentruFront.equals("Formula este valida"))
                    return mesajPentruFront;
            }
        }
        return mesajPentruFront;
    }

    String parsareCriterii(String criterii) {

        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }
        antet = antet.replaceAll("\\s+", " ");
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);

        String[] list = criterii.split(";");
        for (String s : list) {
            Formula criteriu = new Formula(s);
            criteriu.parsareCriteriu();
            mesajPentruFront = criteriu.getMesajPentruFront();
            if (!mesajPentruFront.equals("Criteriul este valid!"))
                return mesajPentruFront;
            else {
                criteriu.verificareVariabileCriteriu(antetMaterie);
                mesajPentruFront = criteriu.getMesajPentruFront();
                if (mesajPentruFront.equals("Criteriul este valid!"))
                    //verificareVariabileCriteriu
                    return mesajPentruFront;
            }
        }
        return mesajPentruFront;
    }

    String verificareOrdineFormule(Formula[] formule, int nrFormule) {
        HashMap<String, Integer> noduri = new HashMap<>();
        int index = -1;
        for (Formula f : formule) {
            for (String var : f.variabile) {
                if (!noduri.containsKey(var) && var != null) {
                    noduri.put(var, ++index);
                    //System.out.println(index + var);
                }
            }
        }

        int nrNoduri = index + 1;
        HashMap<Integer, Integer> indexFormula = new HashMap<>();
        index = -1;
        int[][] graf = new int[nrNoduri][nrNoduri];
        for (Formula f : formule) {
            int primaVariabila = -1;
            for (String var : f.variabile) {
                if (primaVariabila == -1 && var != null) {
                    primaVariabila = noduri.get(var);
                    indexFormula.put(primaVariabila, ++index);
                } else if (var != null) {
                    graf[primaVariabila][noduri.get(var)] = 1;
                }
            }
        }

        int[][] matriceaDrumurilor = new int[nrNoduri][nrNoduri];
        for (int i = 0; i < nrNoduri; i++)
            for (int j = 0; j < nrNoduri; j++)
                matriceaDrumurilor[i][j] = graf[i][j];

        for (int k = 0; k < nrNoduri; ++k)
            for (int i = 0; i < nrNoduri; ++i)
                for (int j = 0; j < nrNoduri; ++j)
                    if (matriceaDrumurilor[i][j] == 0)
                        matriceaDrumurilor[i][j] = matriceaDrumurilor[i][k] * matriceaDrumurilor[k][j];

        for (int i = 0; i < nrNoduri; i++)
            if (matriceaDrumurilor[i][i] == 1) {
                mesajPentruFront = "Eroare: Formulele nu sunt independente - exista circuite in calculul punctajului";
                return mesajPentruFront;
            }

        int[] ordFormule = new int[nrFormule];
        boolean[] visited = new boolean[nrNoduri];
        index = -1;
        while (index != nrFormule - 1)
            index = BFS(0, visited, graf, nrNoduri, ordFormule, index);

        String ordineFormule = "";
        for (int i = nrFormule - 1; i >= 0; i--) {
            ordineFormule += String.valueOf(indexFormula.get(ordFormule[i]));
            if (i > 0)
                ordineFormule += " ";
        }

        return ordineFormule;
    }

    int BFS(int i, boolean[] visited, int[][] graf, int nrNoduri, int[] ordineFormule, int index) {
        Queue<Integer> q = new ArrayDeque<>();

        visited[i] = true;
        q.add(i);

        while (!q.isEmpty()) {
            i = q.poll();
            for (int j = 0; j < nrNoduri; j++) {
                if (!visited[j]) {
                    visited[j] = true;
                    q.add(j);
                }
            }

            int ok = 0, j = 0;
            while (j < nrNoduri && ok == 0) {
                if (graf[i][j] != 0) {
                    ordineFormule[++index] = i;
                    ok = 1;
                    //System.out.println(ordineFormule[index]);
                }
                j++;
            }
        }

        return index;
    }

    void generareCriterii(String id_materie, String criterii) {

    }

    String generareFormula(String id_materie, String formula) {

        String antet = prelucrareDate.functiiGestiune.selectAntet(id_materie);
        if (antet.equals("")) {
            mesajPentruFront = "Eroare: Antetul nu a fost definit";
            return mesajPentruFront;
        }

        Formula formulaNoua = new Formula(formula);
        formulaNoua.parsareFormula();
        mesajPentruFront = formulaNoua.getMesajPentruFront();
        if (!mesajPentruFront.equals("Formula este valida"))
            return mesajPentruFront;

        antet = antet.replaceAll("\\s+", " ");
        String[] campuriAntet = antet.split(" ");
        AntetMaterie antetMaterie = new AntetMaterie(campuriAntet);
        formulaNoua.verificareVariabileFormula(antetMaterie);
        mesajPentruFront = formulaNoua.getMesajPentruFront();
        if (mesajPentruFront.equals("Formula este valida"))
            this.prelucrareDate.functiiGestiune.updateFormula(id_materie, formula);

        return mesajPentruFront;

    }


    String generareAntet(String id_materie, String antet) {

        antet = antet.replaceAll("\\s+", " ");
        String[] sir = antet.split(" ");
        AntetMaterie antetNou = new AntetMaterie(sir);
        antetNou.verificareAntetMaterie();
        mesajPentruFront = antetNou.getMesajPentruFront();

        if (mesajPentruFront.equals("Antetul este valid"))
            this.prelucrareDate.functiiGestiune.updateAntet(id_materie, antet);

        return mesajPentruFront;


    }

    void schimbareAntet(String id_materie, String antet) {

    }


    void schimbareFormula(String id_materie, String formula) {

    }


    void schimbareCriterii(String id_materie) {

    }


}
