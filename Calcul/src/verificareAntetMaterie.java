public class verificareAntetMaterie {

    private String regex="[0-9][a-zA-Z]+";
     public verificareAntetMaterie(String name, String name1)
     { this.name = name1; }

    public String getName() {
        return name;
    }

    public void name(String[] campuriAntet) {
        for(String s: campuriAntet)
        {
            if(System.out.println(campuriAntet.contains("max"))==assertTrue())
                System.out.println("mesajPentruFront");
            if(System.out.println(campuriAntet.contains("sum"))==assertTrue())
                System.out.println("mesajPentruFront");
            if(System.out.println(campuriAntet.contains("min"))== assertTrue() )
                System.out.println("mesajPentruFront");
            if (System.out.println(campuriAntet.matches(regex))=assertFalse())
                System.out.println("mesajPentruFront");


        }
    }

}
