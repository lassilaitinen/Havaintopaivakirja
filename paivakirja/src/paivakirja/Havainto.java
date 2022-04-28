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
public class Havainto implements Cloneable {
    //Havainnon tietokentät attribuutteina:
    private int idnro;
    private Laji elain;
    private String nimi = "";
    private String paikka = "";
    private String maara = "";
    private String muuta = "";
    private String pyydetty = "";
    private String pvm = "";
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
     * Palauttaa lajiin liitetyn lajin
     * @param lajinimi Lajin nimi
     * @return Havaintoon liitetty laji
     */
    public Laji tamaLaji(@SuppressWarnings("unused") String lajinimi) {
        return elain;
    }
    
    
    /**
     * Asetetaan havainnolle eläin/laji
     * @param nimi Lajin nimi
     */
    public void setElain(String nimi) {
        Laji laji = Lajit.annaLaji(nimi);
        if (laji == null) laji = new Laji(nimi);
        elain = laji;
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
    
    
    /**
     * Palauttaa havainnon lajin nimen
     * @return haluttu laji
     */
    public String getLaji() {
        
        return this.nimi;
    }    
    
    
    /** Palauttaa havainnon paikan
     * @return haluttu paikka
     */
    public String getPaikka() {
        return this.paikka;
    }
    

    /**
     * Palauttaa havainnon lajin määrän
     * @return halutun havainnon määrän
     */
    public String getMaara() {
        return this.maara;
    }
    
    
    /**
     * Palauttaa havainnon lajin päivämäärän
     * @return halutun havainnon päivämäärän
     */
    public String getPvm() {
        return this.pvm;
    }
    
    
    /**
     * Palauttaa havainnon lajin lisätiedot
     * @return halutun havainnon lisätiedot
     */
    public String getLisat() {
        return this.muuta;
    }

    
    /**
     * Palauttaa havainnon lajin pyyntötilanteen
     * @return pyydettiinkö kun havaittiin
     */
    public String getPyydettiinko() {
        return this.pyydetty;
    }
    
    
    /*
     * Kloonataan jäsen
     * @return kloonattu jäsen
     */
    @Override
    public Havainto clone() throws CloneNotSupportedException {
        Havainto uusi;
        uusi = (Havainto) super.clone();
        return uusi;
    }
    
    
    @Override
    public String toString() {
        return "" + getID() + "|" + nimi + "|" +
                paikka + "|" + maara + "|" + 
                pvm + "|" + muuta + "|" + pyydetty;
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
        pyydetty = Mjonot.erota(sb, '|', pyydetty);
    }
    
    
    private void setID(int nro) {
        idnro = nro;
        if (idnro >= nextid) nextid = idnro + 1;   
    }
    
    
    /**
     * asettaa havainnon tiedot
     * @param i mihin kenttään uusi teksti asetetaan
     * @param s uusi lajin nimi
     * @return virhe, jos laji ei käy, muutoin null
     * @example
     * <pre name="test">
     * Havainto h = new Havainto();
     * h.aseta(1, "hirvi") === null;
     * h.aseta(4, "kesä 2017") === null;
     * h.aseta(4, "24.2.2022") === null;
     * </pre>
     */
    public String aseta(int i, String s) {
        String jono = s.trim();
        StringBuffer sb = new StringBuffer(jono);
         switch (i) {
        case 0:
            setID(Mjonot.erota(sb, '|', getID()));
            return null;
        case 1:
            nimi = jono;
            return null;
        case 2:
            paikka = jono;
            return null;
        case 3: 
            maara = jono;
            return null;
        case 4:
            pvm = jono;
            return null;
        case 5:
            muuta = jono;
            return null;
        case 6:
            pyydetty = jono;
            return null;
        default:
            return "Kokeile uudestaan";
        }
    }


    /**
     * Tulostetaan havainnon tiedot
     * @param out Tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(/*String.format("%05d", idnro, 5) + " " + */"Laji: " + nimi);
        out.println("Paikka: " + paikka);
        out.println("Määrä: " + maara);
        out.println("Päivämäärä :" + pvm);
        out.println("Muuta: " + muuta);
        out.println("Pyydettiinkö: " + pyydetty);
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
     * Palautetaan GridPanen kentän teksti
     * @param i Minkä kohdan teksti halutaan
     * @return Haluttu teksti
     */
    public String getTeksti(int i) {
        switch (i) {
        case 0: return "Havainnon tiedot";
        case 1: return "Laji:";
        case 2: return "Paikka:";
        case 3: return "Määrä:";
        case 4: return "Päivämäärä:";
        case 5: return "Lisätietoja:";
        case 6: return "Pyydettiinkö:";
        default: return "Miten meni noin omasta mielestä?";
        }
    }
    
    
    /**
     * Asetetaan haluttuun kenttään haluttu sisältö
     * @param i Minkä kentän sisältö asetetaan
     * @return Haluttu sisältö
     */
    public String anna(int i) {
        switch (i) {
        case 0: return " ";
        case 1: return "" + nimi;
        case 2: return "" + paikka;
        case 3: return "" + maara;
        case 4: return "" + pvm;
        case 5: return "" + muuta;
        case 6: return "" + pyydetty;
        default: return "Ei onnistu!";
        }
    }
    
    
    /**
     * Tehdään testiarvot havainnolle
     */
    public void vastaa() {
        nimi = "sorkkaeläin";
        paikka = "metsä";
        maara = "2";
        pvm = "24.2.2022";
        muuta = "Sarvet pudonnut";
        pyydetty = "kyllä";
        elain = Lajit.annaLaji(nimi);
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
