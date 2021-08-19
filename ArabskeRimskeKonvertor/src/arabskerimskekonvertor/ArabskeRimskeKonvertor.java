
package arabskerimskekonvertor;

import java.util.Scanner;

public class ArabskeRimskeKonvertor {   
    
    //funkcia ktora vrati cislo prisluchajuce zadanej rimskej cislici alebo -404, ked dostane neplatnu cislicu
    public static int rimskaCislicaNaCislo(char pismeno) { 
        switch(pismeno) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -404;
        }
    }
    
    public static String arabskeNaRimske(int arabskeCislo) {
        if(arabskeCislo > 0 && arabskeCislo < 5000) {
            //polia arabskych cisel a im prisluchajucich rimskych cisel
            int[] arabske = new int[]{1000,900,500,400,100,90,50,40,10,9,5,4,1};
            String[] rimske = new String[]{"M","DM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

            String rimskeCislo = "";
            int i = 0;
            while(arabskeCislo > 0 && i < arabske.length) {
                //kym je cislo v poli arabskych cisel na pozicii i mensie ako zadane arabske cislo
                //tak sa k rimskemu cislu prida prislusna cislica a odcita sa od arabskeho cisla
                //odcitava sa kym nie je vstupne cislo 0 (podmienka vo while)
                if (arabskeCislo >= arabske[i]) {
                    rimskeCislo += rimske[i];
                    arabskeCislo -= arabske[i];
                } else {
                    i++; //v opacnom pripade sa posunie pozicia v poli arabskych cisel na mensie cislo
                }
            }
            return rimskeCislo;
        } else {
            throw new NumberFormatException("Cislo nie je v rozsahu 1-4999");
        }  
    }
    
    public static int rimskeNaArabske(String rimskeCislo) {
        int i = 0;
        int arabskeCislo = 0;
     
        while(i < rimskeCislo.length()) {
            //prechadza sa rimske cislo znak po znaku
            char pismeno = rimskeCislo.charAt(i);
            //ciselna reprezentacia rimskej cislice
            int cislo = rimskaCislicaNaCislo(pismeno);
            //ak nebude vystup z funkcie rimskaCislicaNaCislo -404, teda sa nenajde neplatny znak, pokracuje sa
            if(cislo > 0) {
                arabskeCislo += cislo;
                if(i > 0) {
                    char predchPismeno = rimskeCislo.charAt(i - 1);
                    int predchCislo =  rimskaCislicaNaCislo(predchPismeno);
                    //tu sa porovna ci hodnota predchadzajucej cislice bola mensia ako hodnota aktualnej (t.j. napr. pri cisle IX)
                    //a ak ano odcita sa. 2-krat preto, lebo sme ju vyssie pricitali tak sa to musi odcitat a potom este raz
                    if(predchCislo < cislo) {
                        arabskeCislo-= 2*rimskaCislicaNaCislo(predchPismeno);
                    }  
                }
                //posun na dalsi znak
                i++;
            } else {
                throw new NumberFormatException("Neznamy znak");
            }   
        }
        
        if (arabskeCislo < 1 || arabskeCislo > 4999) {
            throw new NumberFormatException("Cislo nie je v rozsahu 1-4999");
        }
        
        return arabskeCislo;     
    }
    
    //vrati true ak vstupny string je integer, inak vrati false
    public static boolean jeCislo(String str) {
        if (str == null) {
            return false;
        }
        try {
            int cislo = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        boolean koniec = false;
        Scanner scanner = new Scanner(System.in);
        while(!koniec) {
            System.out.println("Zadajte rimske alebo arabske cislo");
            System.out.println("pre ukoncenie zadajte 0");
            String vstup = scanner.next();
            
            if(vstup.equals("0")) {
                koniec = true;
            } else {
                //ak je vstup cislo, konvertuje sa automaticky arabske na rimske
               if(jeCislo(vstup)) {
                   try{
                       System.out.println("Vystup: " + ArabskeRimskeKonvertor.arabskeNaRimske(Integer.parseInt(vstup)));
                   } catch(NumberFormatException e) {
                       System.out.println(e);
                   }
               } else {
                   try{
                       System.out.println("Vystup: " + ArabskeRimskeKonvertor.rimskeNaArabske(vstup));
                   } catch(NumberFormatException e) {
                       System.out.println(e);
                   }
               }
            }
        }
        scanner.close();
    } 
}
