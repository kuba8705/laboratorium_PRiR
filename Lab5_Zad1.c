
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>

double f(double x)
{
  return(4 * x * x - 6 * x + 5);
}

int main()
{
  pid_t pid;
  pid = fork();
  const int N = 1000 + rand() % 500; //liczba punktów/trapezów podziałowych
  double xp,xk,s,dx;
  int i;

  if(pid==0) sleep(3);

  srand(time(NULL));

  // Poczatek całkowania

  xp = rand() % 50;
  
  // Koniec całkowania

  xk = rand() % 50 + 100;

  //Obliczanie
  s  = 0;
  dx = (xk - xp) / N;
  for(i = 1; i < N; i++) s += f(xp + i * dx);
  s = (s + (f(xp) + f(xk)) / 2) * dx;

  printf("Wartosc calki wynosi: %f\n",s);
  printf("Poczatek calkowania: %f\n",xp);
  printf("Koniec calkowania: %f\n",xk);
  printf("Liczba punktów/trapezów podziałowych: %d\n",N);
  return 0;
}
