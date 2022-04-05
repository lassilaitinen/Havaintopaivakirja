/**
 * 
 */
package paivakirja;

import java.io.File;

/**
 * Päiväkirja-luokka
 *|------------------------------------------------------------------------|
 *| Luokan nimi:   Päiväkirja                          | Avustajat:        |
 *|-------------------------------------------------------------------------
 *| Vastuualueet:                                      |                   | 
 *|                                                    | - Havainnot       | 
 *| - Huolehtii Havainto - ja Laji -luokkien välisestä | - Lajit           |
 *|   yhteistyöstä                                     | - Havainto        |
 *| - välittää em. luokkien tietoja jos niitä          | - Laji            | 
 *|   pyydetään                                        |                   |
 *| - lukee ja kirjoittaa päiväkirjan tiedostoon       |                   | 
 *|   avustajien avulla                                |                   |
 *|                                                    |                   | 
 *|-------------------------------------------------------------------------
 * @author lassi
 * @version 27.2.2022
 *
 */
public class Paivakirja {
    private Havainnot havainnot = new Havainnot();
    private Lajit lajit = new Lajit();
    private String hakemisto = "paivakirja";
    
    
    /**
     * Palauttaa havaintojen lukumäärän
     * @return havaintojen lukumäärä
     */
    public int getLukumaara() {
        return havainnot.getLkm();
    }
    
    
    /**
     * Havainnon lisääminen
     * @param havainto Havainto joka lisätään
     * @throws TilaException Virheilmoitus jos ei voida lisätä
     * @example
     * <pre name="test">
     * #THROWS TilaException 
     * Paivakirja paivakirja = new Paivakirja();
     * Havainto panda = new Havainto();
     * Havainto tiikeri = new Havainto();
     * paivakirja.getLukumaara() === 0;
     * paivakirja.lisaa(panda); paivakirja.getLukumaara() === 1;
     * paivakirja.lisaa(tiikeri); paivakirja.getLukumaara() === 2;
     * paivakirja.lisaa(tiikeri); paivakirja.getLukumaara() === 3;
     * paivakirja.annaHavainto(0) === panda;
     * paivakirja.annaHavainto(1) === tiikeri;
     * paivakirja.annaHavainto(2) === tiikeri;
     * paivakirja.annaHavainto(1) == panda === false;
     * paivakirja.annaHavainto(1) == tiikeri === true;
     * paivakirja.annaHavainto(3) === panda; #THROWS IndexOutOfBoundsException 
     * paivakirja.lisaa(panda); paivakirja.getLukumaara() === 4;
     * paivakirja.lisaa(tiikeri); paivakirja.getLukumaara() === 5;
     * </pre>
     */
    public void lisaa(Havainto havainto) throws TilaException {
        havainnot.lisaa(havainto);
    }
    
    
    /**
     * Lisätään uusi laji
     * @param otus Lisättävä laji
     */
    public void lisaa(Laji otus) {
        lajit.lisaa(otus);
    }
    
    
    /**
     * Palauttaa i:n havainnon
     * @param i Monesko havainto halutaan
     * @return Halutun havainnon viite
     * @throws IndexOutOfBoundsException Virheilmoitus jos i ei kelpaa
     */
    public Havainto annaHavainto(int i) throws IndexOutOfBoundsException {
        return havainnot.anna(i);
    }
    
    
    /**
     * Haetaan havainnon lajitiedot
     * @param havainto Havainto, jonka lajin tiedot haetaan
     * @return Havainnon lajin tiedot
     */
    public Laji annaLaji(Havainto havainto) {
        return lajit.anna(havainto.getLaji(havainto));
    }
    
    
    /**
     * Lukee havainnot tiedostosta
     * @param nimi Tiedosto
     * @throws TilaException Virheilmoitus jos ei onnistu
     */
    public void lueTiedosto(String nimi) throws TilaException {
        File doc = new File(nimi);
        doc.mkdir();
        havainnot = new Havainnot();
        lajit = new Lajit();
        hakemisto = nimi;
        
        havainnot.lueTiedosto(nimi);
        lajit.lueTiedosto(nimi);
    }
    
    
    /**
     * Tallentaa havainnot tiedostoon
     * @throws TilaException Virheilmoitus jos tallennus ei onnistu
     */
    public void tallenna() throws TilaException {
        String virhe = "";
        
        // Havaintojen tallentaminen
        try {
            havainnot.tallenna(hakemisto);
        } catch (TilaException e) {
            virhe = e.getMessage();
        }
        
        // Lajien tallentaminen
        try {
            lajit.tallenna(hakemisto);
        } catch (TilaException e) {
            virhe += e.getMessage();
        }
        
        if (!"".equals(virhe)) throw new TilaException(virhe);
        
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paivakirja paivakirja = new Paivakirja();
        
        try {
            paivakirja.lueTiedosto("paivakirja");
        } catch (TilaException e) {
            System.err.println(e.getMessage());
        }
        
        Havainto hirvi = new Havainto();
        Havainto kauris = new Havainto();
                
        hirvi.rekisterointi();
        kauris.rekisterointi();
        hirvi.vastaa();
        kauris.vastaa();
        
        try {                
            paivakirja.lisaa(hirvi);
            paivakirja.lisaa(kauris);
            Laji otus = new Laji();
            Laji elio = new Laji();
            otus.vastaa(0);
            elio.vastaa(1);
            paivakirja.lisaa(otus);
            paivakirja.lisaa(elio);

            for (int i = 0; i < paivakirja.getLukumaara(); i++) {
                Havainto havainto = paivakirja.annaHavainto(i);
                havainto.tulosta(System.out);
                Laji haluttu = paivakirja.annaLaji(havainto);
                haluttu.tulosta(System.out);
            }
            paivakirja.tallenna();

        } catch (TilaException viesti) {
            System.err.println(viesti.getMessage());
        }
    
    }

}
