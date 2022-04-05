/**
 * 
 */
package paivakirja;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
    /*private int minkoko = 1;
    private int maxkoko = 10;
    private String yksikko = " ";*/
    private String koko = "1-10 kg";
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
     * Palauttaa halutun lajin
     * @return Lajin nimen
     */
    public String getLaji() {
        return laji;
        //return "Lajia " + nro + " ei löydy";
    }
    
    /** Asettaa lajin
     * @param nimi Lajin nimi
     */
    public void setLaji(String nimi) {
        this.laji = nimi;
    }
    
    
    /**
     * Tulostetaan havainnon tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Laji: " +  laji);
        out.println("Luokka: " + luokka);
        out.println("Uhanalaisuus: " + uhanalaisuus);
        out.println("Koko :" + koko);
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
    
    
    private void setID(int nro) {
        idnro = nro;
        if (idnro >= nextid) nextid = idnro + 1;
        
    }
    
    
    /**
     * Lajin tiedot merkkijonona tiedostoon tallentamista varten
     */
    @Override
    public String toString() {
        return "" + getID() + "|" + laji + "|" +
                luokka + "|" + uhanalaisuus + "|" + 
                koko + "|" + 
                tuntomerkit + "|" + ravinto;
    }
    
    
    /**
     * selvitetään tiedot merkkijonosta, joka on eroteltu |-merkillä
     * @param mjono rivi, josta tiedot erotellaan
     * @example
     * <pre name="test">
     * Laji l = new Laji();
     * l.parse("1   |  Hauki    | Kala");
     * l.getID() === 1;
     * l.toString().startsWith("1|Hauki|Kala|") === true;
     * l.rekisterointi();
     * </pre>
     */
    public void parse(String mjono) {
        StringBuilder sb = new StringBuilder(mjono);
        setID(Mjonot.erota(sb, '|', getID()));
        laji = Mjonot.erota(sb, '|', laji);
        luokka = Mjonot.erota(sb, '|', luokka);
        uhanalaisuus = Mjonot.erota(sb, '|', uhanalaisuus);
        koko = Mjonot.erota(sb, '|', koko);
        tuntomerkit = Mjonot.erota(sb, '|', tuntomerkit);
        ravinto = Mjonot.erota(sb, '|', ravinto);
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
        koko = "0-2 kg";
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
