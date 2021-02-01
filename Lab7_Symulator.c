#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include "mpi.h"

#define WOLNE_MIEJSCE 1
#define NIEDOJRZALY 2
#define DOJRZALY 3
#define ZGNILY 4
#define PODLEJ 500

int liczba_procesow;
int nr_procesu;
int tag = 1;
int wyslij[2];
int odbierz[2];
int zmiana_pory = 10;
MPI_Status mpi_status;

void Wyslij(int nr_kukurydzy, int stan) //wyslij do farmy swoj stan
{
  wyslij[0] = nr_kukurydzy;
  wyslij[1] = stan;
  MPI_Send(&wyslij, 2, MPI_INT, 0, tag, MPI_COMM_WORLD);
  sleep(1);
}

void Farma()
{
  printf("Witamy na Farmie Kukurydzy \n");
  int nr_kukurydzy, status;
  int ilosc_kukurydzy = liczba_procesow - 1;
  int Godzina = 8;
  int ilosc_plonow = 0;
  int ilosc_odpadkow = 0;

  sleep(2);

  while(1)
  {
    if(Godzina >= 24) Godzina = 0;
    else Godzina++;

    if(Godzina == 20)
    {
      //O 20 odpady sa usuwane
      ilosc_plonow = 0;
      printf("Odpadki zastaly usuniete\n");      
    }
    else if(Godzina == 16)
    {
      //O 16 plony sa odwozone na targ
      ilosc_odpadkow = 0;
      printf("Odpadki zastaly odwiezione\n");
    }

    MPI_Recv(&odbierz, 2, MPI_INT, MPI_ANY_SOURCE, tag, MPI_COMM_WORLD, &mpi_status); //odbieram od kogokolwiek
    nr_kukurydzy = odbierz[0];
    status = odbierz[1];

    if (status == 3)
    {
      printf("kukurydza %d zostala zebrana\n", nr_kukurydzy);
      ilosc_plonow++;
    }
    if (status == 4)
    {
      printf("kukurydza %d zostala usunieta\n", nr_kukurydzy);
      ilosc_odpadkow++;
    }
    printf("Godzina: %d, Ilosc plonow: %d, Ilosc odpadkow: %d\n",Godzina,ilosc_plonow,ilosc_odpadkow);
  }
}

void kukurydza()
{
  
  int stan, suma, i,woda;
  stan = NIEDOJRZALY;
  
  while (1)
  {
    if(stan == 1)
    {
      stan = NIEDOJRZALY;
      printf("kukurydza %d, zostala posadzona.\n", nr_procesu);
      woda = PODLEJ;
      //zuzywamy troche wody
      srand(nr_procesu);
      woda = woda - (rand() % 500);
      printf("kukurydza %d, zostala podlana.\n", nr_procesu);

      Wyslij(nr_procesu, stan);
    }
    else if (stan == 2)
    {

      if(woda < 150) stan = ZGNILY;
      else stan = DOJRZALY;

      Wyslij(nr_procesu, stan);
    }
    else if (stan == 3)
    {
      stan = WOLNE_MIEJSCE;
      Wyslij(nr_procesu, stan);
    }
    else if (stan == 4)
    {
      stan = WOLNE_MIEJSCE;
      Wyslij(nr_procesu, stan);
    }
    srand(nr_procesu);
    sleep((rand() % 3) + 2);
  }
  
}


int main(int argc, char *argv[])
{
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &nr_procesu);
  MPI_Comm_size(MPI_COMM_WORLD, &liczba_procesow);
  if (nr_procesu == 0)
    Farma(liczba_procesow);
  else
    kukurydza();
  MPI_Finalize();
  return 0;
}