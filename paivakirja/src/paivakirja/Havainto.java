/**
 * 
 */
package paivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Havainto-luokka
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Havainto                            | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    |                   | 
 * | - tietää havainnot tiedot (laji, päivämäärä, paikka|                   |
 * |    ja isätietoja)                                  |                   |
 * | - tarkistaa syntaksin oikeaksi ja muuttaa |-merkin |                   | 
 * |   erottaman merkkijonon havainnon tiedoiksi        |                   |
 * | - osaa antaa merkkijonoina halutun kentän tiedot   |                   | 
 * |   ja laittaa merkkijonon kentäksi n                |                   |
 * |                                                    |                   | 
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author lassi
 * @version 27.2.2022
 *
 */
public class Havainto {
    //Havainnon tietokentät attribuutteina:
    private int idnro;
    private String laji = "";
    private String paikka = "";
    private int maara = 0;
    private int paiva = 1;
    private int kk = 1;
    private int vuosi = 2022;
    private String muuta = "";
    
    private static int nextid = 1;
    
    
    /**
     * Aliohjelma antaa havainnolle id-numeron
     * @return Palauttaa havainnon tunnusnumeron
     * @example
     * <pre name="test">
     *   Havainto panda = new Havainto();
     *   panda.getID() === 0;
     *   panda.rekisterointi();
     *   Havainto tiikeri = new Havainto();
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
        out.println(String.format("%05d", idnro, 5) + " " + laji);
        out.println("Paikka: " + paikka);
        out.println("Määrä: " + maara);
        out.println("Päivämäärä :" + paiva + "." + kk + "." + vuosi);
        out.println("Muuta: " + muuta);
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
     */
    public void vastaa() {
        laji = "sorkkaeläin";
        paikka = "metsä";
        maara = 2;
        paiva = 24;
        kk = 2;
        vuosi = 2022;
        muuta = "Sarvet pudonnut";
    }

    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Havainto hirvi = new Havainto();
        Havainto kauris = new Havainto();
        
        hirvi.rekisterointi();
        kauris.rekisterointi();
        
        hirvi.vastaa();
        kauris.vastaa();
        
        hirvi.tulosta(System.out);
        kauris.tulosta(System.out);
    }

}
