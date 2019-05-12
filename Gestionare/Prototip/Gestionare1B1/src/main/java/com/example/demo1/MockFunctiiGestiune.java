package com.example.demo1;
public class MockFunctiiGestiune {

    public void updateNote(String id_s, String id_m, String note) {

        System.out.println(note);
    }

    public String selectNote(String id_s, String id_m) {

        return "L1 4 L2 4";
    }


    public String selectFormula(String id) {
        return "L1=L2+L3";
    }

    public void updateFormula(String id_m,String formula)
    {

    }

    public String selectAntet(String id) {
        return "L1 L2 L3";
    }

    public void updateAntet(String id_m,String antet)
    {

    }

    public String selectCriterii(String id) {
        return "L1>5; L2>0";
    }

    public void updateCriterii(String id_m,String criterii) {

    }

    public void updatePromovare(String promovare){

    }





}
