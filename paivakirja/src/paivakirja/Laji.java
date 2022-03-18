/**
 * 
 */
package paivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

/**
|------------------------------------------------------------------------|
| Luokan nimi:   Laji                                | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   | 
|                                                    |                   | 
| - tietää lajin tiedot (koko, luokittelu, väri yms.)|                   |
| - tarkistaa syntaksin oikeaksi ja muuttaa |-merkin |                   | 
|   erottaman merkkijonon havainnon tiedoiksi        |                   |
| - osaa antaa merkkijonoina halutun kentän tiedot   |                   | 
|   ja laittaa merkkijonon kentäksi n                |                   |
|                                                    |                   | 
|                                                    |                   | 
|-------------------------------------------------------------------------
 * @author lassi
 * @version 7.3.2022
 *
 */
public class Laji {
    //TODO MUUTA! Lajin tietokentät attribuutteina:
    private int idnro;
    private String laji = "";
    private String luokka = "";
    private String uhanalaisuus = "";
    private int minkoko = 1;
    private int maxkoko = 10;
    private String tuntomerkit = "";
    private String ravinto = "";
    
    private static int nextid = 1;
    
    
    /**
     * Aliohjelma antaa havainnolle id-numeron
     * @return Palauttaa havainnon tunnusnumeron
     * @example
     * <pre name="test">
     *   Laji panda = new Laji();
     *   panda.getID() === 0;
     *   panda.rekisterointi();
     *   Laji tiikeri = new Laji();
     *   tiikeri.rekisterointi();
     *   int n1 = panda.getID();
     *   int n2 = tiikeri.getID();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisterointi() {
        idnro = nextid;
        nextid++;
        return idnro;
    }
    
    /**
     * Palauttaa halutun ID:n
     * @return Palauttaa halutun havainnon ID:n
     */
    public int getID() {
        return idnro;
    }
    
    
    /**
     * Tulostetaan havainnon tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(/*String.format("%05d", idnro, 5) + " " +*/ laji);
        out.println("Luokka: " + luokka);
        out.println("Uhanalaisuus: " + uhanalaisuus);
        out.println("Koko :" + minkoko + " - " + maxkoko);
        out.println("Tuntomerkit: " + tuntomerkit);
        out.println("Ravinto: " + ravinto);
        out.println("---------");
    }
    
    
    /**
     * Tulostetaan havainnon tiedot
     * @param os Tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Tehdään testiarvot havainnolle
     * @param nro Viite, minkä lajin tiedot kyseessä
     */
    public void vastaa(int nro) {
        idnro = nro;
        laji = "sorkkaeläin";
        luokka = "metsä";
        uhanalaisuus = "elinvoimainen";
        minkoko = 0;
        maxkoko = 2;
        tuntomerkit = "Sarvet päässä";
        ravinto = "Ruoho";
    }
    

    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laji hirvi = new Laji();
        Laji kauris = new Laji();
        
        hirvi.rekisterointi();
        kauris.rekisterointi();
        
        hirvi.vastaa(0);
        kauris.vastaa(1);
        
        hirvi.tulosta(System.out);
        kauris.tulosta(System.out);
    }

}
