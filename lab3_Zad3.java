import java.util.Random;

class Autobus extends Thread {
    static int ZAJEZDNIA = 1;
    static int START = 2;
    static int W_TRASIE = 3;
    static int WYPADEK = 4;
    static int TANKUJ = 1000;
    static int REZERWA = 200;

    int numer;
    int paliwo;
    int stan;
    Random rand;

    Dworzec_Autobusowy dworzec;

    public Autobus(int numer,Dworzec_Autobusowy dworzec) {
        rand = new Random();
        this.numer = numer;
        this.dworzec = dworzec;
        this.stan = ZAJEZDNIA;
    }

    public void run() {
        while (true) {

            if(rand.nextInt(100)==1) stan = WYPADEK;

            if (stan == ZAJEZDNIA) {
                paliwo = TANKUJ;
                stan = START;
                System.out.println("Autobus o numerze " + numer + " przygotowuje sie do wyjazdu");

                try {
                    sleep(1000);
                } catch (Exception e) {
                }

            } else if (stan == START) {
                stan = dworzec.start(numer);
            } else if (stan == W_TRASIE) {
                paliwo -= rand.nextInt(400);
                System.out.println("Autobus " + numer + " na trasie");
                if (paliwo <= REZERWA) {
                    stan = ZAJEZDNIA;
                    System.out.println("Autobus " + numer + " jest na rezerwie. Musi wrocic do zajezdni");
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                    }
                    dworzec.zajezdnia(numer);
                }
            } else if (stan == WYPADEK) {
                System.out.println("Samochod o numerze " + numer + " zostal zniszczony w wypadku");

                try {
                    sleep(1000);
                } catch (Exception e) {
                }

                dworzec.zmniejsz(numer);
                this.stop();
            }
        }
    }
}

class Dworzec_Autobusowy {

    static int ZAJEZDNIA = 1;
    static int START = 2;
    static int W_TRASIE = 3;
    static int WYPADEK = 4;

    int ilosc_lini;
    int ilosc_autobusow_w_trasie = 0;

    public Dworzec_Autobusowy(int ilosc_lini,int miejsca_w_zajezdni) {
        this.ilosc_lini = ilosc_lini;
    }
    synchronized int start(int nr)
    {
        if(ilosc_autobusow_w_trasie<ilosc_lini)
        {
            System.out.println("Autobus o numerze "+nr+" wyjechal z dworca na linie");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            ilosc_autobusow_w_trasie++;
            return W_TRASIE;
        }
        else return START;
    }
    synchronized void zajezdnia(int nr)
    {
        System.out.println("Autobus o numerze "+nr+" wrocil do zajezdni");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        ilosc_autobusow_w_trasie--;
    }
    synchronized void zmniejsz(int nr)
    {
        System.out.println("Autobus o numerze "+nr+" zostal usuniety z lini");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        ilosc_autobusow_w_trasie--;
    }
}

public class lab3_Zad3 {

    public static void main(String[] args) {

        int ilosc_autobusow = 45;

        Dworzec_Autobusowy dworzec = new Dworzec_Autobusowy(35, ilosc_autobusow);
        for (int i = 0; i < ilosc_autobusow; i++)
            new Autobus(i, dworzec).start();

    }
}