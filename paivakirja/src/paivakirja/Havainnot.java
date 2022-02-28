/**
 * 
 */
package paivakirja;

/**
 * Havainnot-luokka
 * |------------------------------------------------------------------------|
 * | Luokan nimi:   Havainnot                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   | 
 * |                                                    | - Havainto        | 
 * | - osaa lisätä ja poistaa havaintoja                |                   |
 * | - pitää yllä varsinaista rekisteriä havannoista    |                   |
 * | - lukee ja kirjoittaa havainnot tiedostoon         |                   | 
 * | - osaa myös etsiä ja lajitella havaintoja          |                   |
 * |   halutulla tavalla                                |                   | 
 * |                                                    |                   |
 * |                                                    |                   | 
 * |                                                    |                   | 
 * |                                                    |                   | 
 * |-------------------------------------------------------------------------
 * @author lassi
 * @version 27.2.2022
 *
 */
public class Havainnot {
    
    private static final int maxmaara = 50;
    private int lkm = 0;
    private String tiedosto = "";
    private Havainto alkiot[] = new Havainto[maxmaara];
    
    
    /**
     * Oletusmuodostaja
     */
    public Havainnot() {
        // Alustettu attribuuteissa
    }

    
    /**
     * Lisää uuden havainnon
     * @param havainto Lisättävän viites
     * @throws TilaException Virheilmoitus, jos tietorakenne täysi
     * @example
     * <pre name="test">
     * #THROWS TilaException 
     * Havainnot havainnot = new Havainnot();
     * Havainto panda = new Havainto();
     * Havainto tiikeri = new Havainto();
     * havainnot.getLkm() === 0;
     * havainnot.lisaa(panda); jasenet.getLkm() === 1;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 2;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 3;
     * havainnot.anna(0) === panda;
     * havainnot.anna(1) === tiikeri;
     * havainnot.anna(2) === tiikeri;
     * havainnot.anna(1) == panda === false;
     * havainnot.anna(1) == tiikeri === true;
     * havainnot.anna(3) === panda; #THROWS IndexOutOfBoundsException 
     * havainnot.lisaa(panda); jasenet.getLkm() === 4;
     * havainnot.lisaa(tiikeri); jasenet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Havainto havainto) throws TilaException{
        if (lkm >= alkiot.length) throw new TilaException("Liian paljon alkioita");
        alkiot[lkm] = havainto;
        lkm++;
    }
    
    
    /**
     * Palauttaa halutun viitteen jäseneen i
     * @param i Jäsen, jonka viite halutaan
     * @return Halutun jäsenen viite
     * @throws IndexOutOfBoundsException jos i ei käy
     */
    public Havainto anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <=i) throw new IndexOutOfBoundsException("Laiton indeksi " + i);
        return alkiot[i];
    }
    
    
    /**
     * @param hakemisto Tiedoston hakemisto
     * @throws TilaException Jos lukeminen epäonnistuu
     */
    public void lueTiedosto(String hakemisto) throws TilaException {
        tiedosto = hakemisto + "/havainnot.dat";
        throw new TilaException("Ei osata lukea tiedostoa: " + tiedosto);
        //TODO: tiedoston lukeminen
    }
    
    
    /**
     * Tallennetan havainnot tiedostoon
     * @throws TilaException jos tallennus ei onnistu
     */
    public void tallenna() throws TilaException {
        throw new TilaException("Ei osata tallentaa tiedostoa: " + tiedosto);
        //TODO: tiedoston tallentaminen
    }
    
    
    /**
     * Palauttaa havaintojen määrän
     * @return havaintojen määrä
     */
    public int getLkm() {
        return lkm;
    }

    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Havainnot havainnot = new Havainnot();
        
        Havainto hirvi = new Havainto();
        Havainto kauris = new Havainto();
        hirvi.rekisterointi();
        kauris.rekisterointi();
        hirvi.vastaa();
        kauris.vastaa();
        
        try {
            havainnot.lisaa(hirvi);
            havainnot.lisaa(kauris);

            System.out.println("TESTATAAN OHJELMAA:");

            for (int i = 0; i < havainnot.getLkm(); i++) {
                Havainto havainto = havainnot.anna(i);
                System.out.println("Havainnon id: " + i);
                havainto.tulosta(System.out);
            }

        } catch (TilaException viesti) {
            System.out.println(viesti.getMessage());
        }

    
    }

}
