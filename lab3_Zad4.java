import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class lab3_Zad4 extends Thread {
    final static int N = 4096;
    final static int CUTOFF = 100;
    static int[][] set = new int[N][N];
    double ci, cr;
    //private static Complex c;

    public static void main(String[] args) throws Exception {
        // Calculate set
        double ci = 0.65, cr = -0.10;
        long startTime = System.currentTimeMillis();
        lab3_Zad4 thread0 = new lab3_Zad4(0,ci,cr);
        lab3_Zad4 thread1 = new lab3_Zad4(1,ci,cr);
        lab3_Zad4 thread2 = new lab3_Zad4(2,ci,cr);
        lab3_Zad4 thread3 = new lab3_Zad4(3,ci,cr);
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();

        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenia zakończone w czasie " + (endTime - startTime) + " millisekund");
        // wyświetlanie rusunku
        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);
        // Rysowanie pixeli
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = set[i][j];
                float level;
                if (k < CUTOFF) {
                    level = (float) k / CUTOFF;
                } else {
                    level = 0;
                }
                Color c = new Color(0, level, 0); // zielony
                img.setRGB(i, j, c.getRGB());
            }
        }
        // zapis do pliku
        ImageIO.write(img, "PNG", new File("E:\\Nauka\\Rownolegle\\lab3\\lab3_Zad4\\Mandelbrot.png"));
    }

    int me;

public lab3_Zad4(int me,double ci,double cr) {
    this.me = me;
    this.ci = ci;
    this.cr = cr;
    }

    public void run() {
        int begin = 0, end = 0;
        if (me == 0) {
            begin = 0;
            end = (N / 4) * 1;
        } else if (me == 1) {
            begin = (N / 4) * 1;
            end = (N / 4) * 2;
        } else if (me == 2) {
            begin = (N / 4) * 2;
            end = (N / 4) * 3;
        } else if (me == 3) {
            begin = (N / 4) * 3;
            end = N;
        }
        for (int i = begin; i < end; i++) {
            for (int j = 0; j < N; j++) {
                double pr = (4.0 * i - 2 * N) / N;
                double pi = (4.0 * j - 2 * N) / N;
                double zr = pr, zi = pi;
                int k = 0;
                while (k < CUTOFF && zr * zr + zi * zi < 4.0) {
                    // z = c + z * z
                    double newr = cr + zr * zr - zi * zi;
                    double newi = ci + 2 * zr * zi;
                    zr = newr;
                    zi = newi;
                    k++;
                }
                set[i][j] = k;
            }
        }
    }
}