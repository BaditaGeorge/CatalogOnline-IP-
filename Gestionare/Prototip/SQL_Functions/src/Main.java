import org.junit.Test;

import static org.junit.Assert.*;

public class Main {


    @Test
    public static void main(String[] args) {
        SQL_func test = new SQL_func("B://faculta/IP/BD_Gestiunea");
          test.selectFormula();
        test.selectFormula("10");
         test.selectNote("1","10");
         test.selectNote();
        test.updateNote("1","12","L1 5; L2 6;");
        test.updateFormula("5","(L1 + L2)/2");
            test.insertMaterii("3","1","PF","L1 5; L2 10 ; L3 15;l4 10;l5 15");
         test.selectCatalog("10","2");
         test.selectDenumireMaterii("1");
        test.insertProfesori("5","Boss","Pavi","13","Bolangerie","L1+L2+L3+L4");
        test.countUsersByName("Marcel");
         test.getSalt("Marcel");
         test.countUsersByMail("da");
         test.countUsersByUsernamePass("Marcel","sfara");
         test.checkAuthCode("Boss","3121");
        test.addNewUser("4","prpr@yahoo.com","sdaffaf","asfaasgdgsdfafas","043323223");
         test.insertProfesori("3","TEST MICULE,TEST");
         test.confirmUser("Pavi");
         test.verificareLogIn("Marcel");
         test.countUsersByMail("prpr@yahoo.com");
         test.selectAntet("10");
        test.updateAntet("ASASASASSAA","1");
        test.insertNewStudent("1123555 Badita George 2B1 bossboschet@grasu.com");
        test.updateCriterii("25","sa te pui bine cu micutu");
        test.updatePromovare("313312325","1","10");
        for(int i=1;i<5;i++)
          test.addSession("boss","1","14:08:2019 16:45");
        test.deleteSession("1");
         test.countSession("1");
         test.getUsername("1");
        test.updateSessionActivity("1","14:05:2019 16:55");
    }
}
