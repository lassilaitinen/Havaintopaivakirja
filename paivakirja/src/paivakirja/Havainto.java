/**
 * 
 */
package paivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
    //private String laji = " ";
    private String nimi = " ";
    private String paikka = " ";
    private int maara = 0;
    /*private int paiva = 1;
    private int kk = 1;
    private int vuosi = 2022;*/
    private String muuta = "";
    
    private String pvm = "1.1.2022";
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
    
    
    /** Palauttaa halutun havainnon lajin
     * @param hav haluttu havainto
     * @return Havainnon laji
     */
    public String getLaji(Havainto hav) {
        return hav.nimi;
    }
    

    
    
    @Override
    public String toString() {
        return "" + getID() + "|" + nimi + "|" +
                paikka + "|" + maara + "|" + 
                pvm + "|" + muuta;
    }
    
    /**
     * selvitetään tiedot merkkijonosta, joka on eroteltu |-merkillä
     * @param mjono rivi, josta tiedot erotellaan
     * @example
     * <pre name="test">
     * Havainto h = new Havainto();
     * h.parse("1   |  Hauki    | Järvi");
     * h.getID() === 1;
     * h.toString().startsWith("1|Hauki|Järvi|") === true;
     * h.rekisterointi();
     * </pre>
     */
    public void parse(String mjono) {
        var sb = new StringBuilder(mjono);
        setID(Mjonot.erota(sb, '|', getID()));
        nimi = Mjonot.erota(sb, '|', nimi);
        paikka = Mjonot.erota(sb, '|', paikka);
        maara = Mjonot.erota(sb, '|', maara);
        pvm = Mjonot.erota(sb, '|', pvm);
        muuta = Mjonot.erota(sb, '|', muuta);
    }
    
    
    private void setID(int nro) {
        idnro = nro;
        if (idnro >= nextid) nextid = idnro + 1;
        
    }



    /**
     * Tulostetaan havainnon tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        //StringBuilder n = new StringBuilder(nimi);
        //nimi = Laji.getLaji(Mjonot.erotaInt(n, idnro));
        out.println(/*String.format("%05d", idnro, 5) + " " + */"Laji: " + nimi);
        out.println("Paikka: " + paikka);
        out.println("Määrä: " + maara);
        out.println("Päivämäärä :" + pvm);
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
        nimi = "sorkkaeläin";
        paikka = "metsä";
        maara = 2;
        pvm = "24.2.2022";
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
