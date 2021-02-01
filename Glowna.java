import java.util.Random;

class Samolot extends Thread {
    // definicja stanu samolotu
    static int LOTNISKO = 1;
    static int START = 2;
    static int LOT = 3;
    static int KONIEC_LOTU = 4;
    static int KATASTROFA = 5;
    static int TANKUJ = 1000;
    static int REZERWA = 500;
    // zmienne pomocnicze
    int numer;
    int paliwo;
    int stan;
    Lotnisko l;
    Random rand;

    public Samolot(int numer, int paliwo, Lotnisko l) {
        this.numer = numer;
        this.paliwo = paliwo;
        this.stan = LOT;
        this.l = l;
        rand = new Random();
    }

    public void run() {
        while (true) {
            if (stan == LOTNISKO) {
                if (rand.nextInt(2) == 1) {
                    stan = START;
                    paliwo = TANKUJ;
                    System.out.println("Na lotnisku prosze o pozwolenie na start, samolot " + numer);
                    stan = l.start(numer);
                } else {
                    System.out.println("Postoje sobie jeszcze troche");
                }
            } else if (stan == START) {
                System.out.println("Wystartowalem, samolot " + numer);
                stan = LOT;
            } else if (stan == LOT) {
                paliwo -= rand.nextInt(500);
                System.out.println("Samolot " + numer + " w powietrzu");
                if (paliwo <= REZERWA) {
                    stan = KONIEC_LOTU;
                } else
                    try {
                        sleep(rand.nextInt(1000));
                    } catch (Exception e) {
                    }
            } else if (stan == KONIEC_LOTU) {
                System.out.println("Prosze o pozowolenie na ladowanie " + numer + " ilosc paliwa " + paliwo);
                stan = l.laduj();
                if (stan == KONIEC_LOTU) {
                    paliwo -= rand.nextInt(500);
                    System.out.println("REZERWA " + paliwo);
                    if (paliwo <= 0)
                        stan = KATASTROFA;
                }
            } else if (stan == KATASTROFA) {
                System.out.println("KATASTROFA samolot " + numer);
                l.zmniejsz();
            }
        }
    }
}

class Lotnisko {
    static int LOTNISKO = 1;
    static int START = 2;
    static int LOT = 3;
    static int KONIEC_LOTU = 4;
    static int KATASTROFA = 5;
    int ilosc_pasow;
    int ilosc_zajetych;
    int ilosc_samolotow;

    Lotnisko(int ilosc_pasow, int ilosc_samolotow) {
        this.ilosc_pasow = ilosc_pasow;
        this.ilosc_samolotow = ilosc_samolotow;
        this.ilosc_zajetych = 0;
    }

    synchronized int start(int numer) {
        ilosc_zajetych--;
        System.out.println("Pozwolenie na start samolotowi " + numer);
        return START;
    }

    synchronized int laduj() {
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception ie) {
        }
        if (ilosc_zajetych < ilosc_pasow) {
            ilosc_zajetych++;
            System.out.println("Pozwolenie ladowanie na pasie " + ilosc_zajetych);
            return LOTNISKO;
        } else {
            return KONIEC_LOTU;
        }
    }

    synchronized void zmniejsz() {
        ilosc_samolotow--;
        System.out.println("ZABILEM");
        if (ilosc_samolotow == ilosc_pasow)
            System.out.println("Ilosc samaoltow jaka sama jak pasow");
    }
}

public class Glowna {
    static int ilosc_samolotow = 10;
    static int ilosc_pasow = 5;
    static Lotnisko lotnisko;

    public Glowna() {
    }

    public static void main(String[] args) {
        lotnisko = new Lotnisko(ilosc_pasow, ilosc_samolotow);
        for (int i = 0; i < ilosc_samolotow; i++)
            new Samolot(i, 2000, lotnisko).start();
    }
}