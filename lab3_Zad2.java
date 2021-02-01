package com.company;

import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.Scanner;

class FilozofProblem1 extends Thread {

    int mojNum;

    public FilozofProblem1(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            lab3_Zad2.widelec[mojNum].acquireUninterruptibly(); //przechwycenie L widelca
            lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].acquireUninterruptibly(); //przechwycenie P widelca
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            lab3_Zad2.widelec[mojNum].release(); //zwolnienie L widelca
            lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].release(); //zwolnienie P widelca
        }
    }
}

class FilozofProblem2 extends Thread {

    int mojNum;

    public FilozofProblem2(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            if (mojNum == 0) {
                lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].acquireUninterruptibly();
                lab3_Zad2.widelec[mojNum].acquireUninterruptibly();
            } else {
                lab3_Zad2.widelec[mojNum].acquireUninterruptibly();
                lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].acquireUninterruptibly();
            }
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            lab3_Zad2.widelec[mojNum].release();
            lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].release();
        }
    }
}

class FilozofProblem3 extends Thread {
    Random losuj;
    int mojNum;

    public FilozofProblem3(int nr) {
        mojNum = nr;
        losuj = new Random(mojNum);
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            int strona = losuj.nextInt(2);
            boolean podnioslDwaWidelce = false;
            do {
                if (strona == 0) {
                    lab3_Zad2.widelec[mojNum].acquireUninterruptibly();
                    if (!(lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].tryAcquire())) {
                        lab3_Zad2.widelec[mojNum].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                } else {
                    lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].acquireUninterruptibly();
                    if (!(lab3_Zad2.widelec[mojNum].tryAcquire())) {
                        lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                }
            } while (podnioslDwaWidelce == false);
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            lab3_Zad2.widelec[mojNum].release();
            lab3_Zad2.widelec[(mojNum + 1) % lab3_Zad2.iloscFilozofow].release();
        }
    }

}

public class lab3_Zad2{

    //static final int MAX = 5;
    static Semaphore[] widelec;// = new Semaphore[MAX];
    static int iloscFilozofow;

    public static void main(String[] args) {

        int wybor;

        System.out.println("Podaj ilosc filozofow: ");

        Scanner scanner = new Scanner(System.in);
        iloscFilozofow = scanner.nextInt();

        while (iloscFilozofow<2 || iloscFilozofow>100)
        {
            System.out.println("Liczba od 2 do 100. Podaj liczbe jeszcze raz");
            iloscFilozofow = scanner.nextInt();
        }

        System.out.println("Podaj numer problemu(1,2,3): ");
        wybor = scanner.nextInt();

        while (wybor<1 || wybor>3)
        {
            System.out.println("Podaj numer raz(1,2,3)");
            wybor = scanner.nextInt();
        }

        scanner.close();

        widelec = new Semaphore[iloscFilozofow];

        for ( int i =0; i<iloscFilozofow; i++) {
            widelec [ i ] = new Semaphore ( 1 ) ;
        }
        for ( int i =0; i<iloscFilozofow; i++) {
            if(wybor == 1) new FilozofProblem1(i).start();
            else if(wybor == 2) new FilozofProblem2(i).start();
            else new FilozofProblem2(i).start();
        }
    }

}




