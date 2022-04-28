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
public class Laji implements Cloneable{
    //Lajin tietokentät attribuutteina:
    private int idnro;
    private String laji = "";
    private String luokka = "";
    private String uhanalaisuus = "";
    private String koko = "";
    private String tuntomerkit = "";
    private String ravinto = "";
    private String pyydettyja = "0";
    
    private static int nextid = 1;
    
    
    /**
     * Muodostaja, jolle tulee parametrinä lajin nimi tai ohje
     * @param ohje nimi/ohje uudelle lajille
     */
    public Laji(String ohje) {
        laji = ohje;
    }
    
    
    /**
     * Muodostaja lajille
     */
    public Laji() {
        //alustettu attribuuteissa
    }
    
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
        Laji apu = new Laji();
        if (laji == null) return apu.laji;
        return laji;
        //return "Lajia " + nro + " ei löydy";
    }
    
    /**
     * Palauttaa halutun lajin luokan
     * @return Lajin luokka
     */
    public String getLuokka() {
        return this.luokka;
    }
    
    
    /**
     * Palauttaa halutun lajin uhanalaisuuden
     * @return Lajin uhanalaisuus
     */
    public String getUhanalaisuus() {
        return this.uhanalaisuus;
    }
    
    
    /**
     * Palauttaa halutun lajin koon
     * @return Lajin koko
     */
    public String getKoko() {
        return this.koko;
    }
    
    
    /**
     * Palauttaa halutun lajin tuntomerkit
     * @return Lajin tuntomerkit
     */
    public String getTuntomerkit() {
        return this.tuntomerkit;
    }
    
    
    /**
     * Palauttaa halutun lajin ravinnon
     * @return Lajin ravinto
     */
    public String getRavinto() {
        return this.ravinto;
    }
    
    
    /**
     * Palauttaa onko lajia pyydetty
     * @return Montako pyydetty
     */
    public String getPyydettyja() {
        return this.pyydettyja;
    }
    
    
    /** Asettaa lajin
     * @param nimi Lajin nimi
     */
    public void setLaji(String nimi) {
        this.laji = nimi;
    }
    
    
    /*
     * Kloonataan laji
     * @return kloonattu laji
     */
    @Override
    public Laji clone() throws CloneNotSupportedException {
        Laji uusi;
        uusi = (Laji) super.clone();
        return uusi;
    }
    
    
    /**
     * Asettaa lajin pyydettyjen määrän oikein
     */
    public void setPyydetyt() {
        int pyydetyt = Mjonot.erotaInt(pyydettyja, 0);
        pyydetyt++;
        pyydettyja = "" + pyydetyt;
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
        out.println("Pyydettyjä: " + pyydettyja);
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
     * Palautetaan GridPanen kentän teksti
     * @param i Minkä kohdan teksti halutaan
     * @return Haluttu teksti
     */
    public String getTeksti(int i) {
        switch (i) {
        case 0: return "Lajin tiedot";
        case 1: return "Lajin nimi:";
        case 2: return "Luokka:";
        case 3: return "Uhanalaisuus:";
        case 4: return "Koko:";
        case 5: return "Tuntomerkit:";
        case 6: return "Ravinto:";
        case 7: return "Pyydettyjä:";
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
        case 1: return "" + laji;
        case 2: return "" + luokka;
        case 3: return "" + uhanalaisuus;
        case 4: return "" + koko;
        case 5: return "" + tuntomerkit;
        case 6: return "" + ravinto;
        case 7: return "" + pyydettyja;
        default: return "Ei onnistu!";
        }
    }
    
    
    /**
     * asettaa lajin tiedon oikealle paikalle
     * @param i mihin kenttään uusi teksti asetetaan
     * @param s teksti joka asetetaan
     * @return virhe, jos ei käy, muutoin null
     * @example
     * <pre name="test">
     * Laji s = new Laji();
     * s.aseta(1, "panda") === null;
     * s.aseta(2, " ") === null;
     * s.aseta(2, "nisäkkäät") === null;
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
            laji = jono;
            return null;
        case 2:
            luokka = jono;
            return null;
        case 3: 
            uhanalaisuus = jono;
            return null;
        case 4:
            koko = jono;
            return null;
        case 5:
            tuntomerkit = jono;
            return null;
        case 6: 
            ravinto = jono;
            return null;
        case 7:
            pyydettyja = jono;
            return null;
        default:
            return "Kokeile uudestaan";
        }
    }
    
    /**
     * Lajin tiedot merkkijonona tiedostoon tallentamista varten
     */
    @Override
    public String toString() {
        
        if (laji == null) return getID() + " | | | | | | | ";
        return "" + getID() + "|" + laji + "|" +
                luokka + "|" + uhanalaisuus + "|" + 
                koko + "|" + 
                tuntomerkit + "|" + ravinto + "|" + pyydettyja;
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
        pyydettyja = Mjonot.erota(sb, '|', pyydettyja);
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
        pyydettyja = "0";
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
