
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <time.h>

double f(int x)
{
  double liczba;
  liczba = 1.0;

  for(int i = 0;i < x; i++)
  {
    double dodac = (1.0)/(3.0 + 2.0 * (i + 1.0));
    if(i % 2 == 0)
    {
      liczba = liczba - dodac;
    }
    else
    {
      liczba = liczba + dodac;
    }
  }
  liczba = 4 * liczba;
  return liczba;
}

int main()
{
  int p;
  printf("Podaj liczbe procesow potomnych: ");
  scanf("%d",&p);

  if(fork() != 0)
  {
    for(int i = 0; i < p - 2; i++) fork();
  }

  srand((int)getpid());
  int N = rand() % 4900 + 101;

  double wynik = f(N);

  printf("\nLiczba wyrazow szeregu: %d\nPrzyblizenie liczby PI: %f\n",N,wynik);
  

  return 0;
}
