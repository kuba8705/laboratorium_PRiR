
#include <stdio.h>
#include "mpi.h"

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

int main ( int argc , char  **argv )
{
  int numprocs, myid;
  int tag,from,to;
  double data,data2;
  MPI_Status status;
  MPI_Init(&argc , &argv );
  MPI_Comm_rank(MPI_COMM_WORLD, &myid );
  MPI_Comm_size(MPI_COMM_WORLD, &numprocs );

  data = f(myid);
  tag = 2010;
  to = myid + 1;
  from = myid - 1;

  if(myid != numprocs - 1)
  {
     MPI_Send(&data, 1, MPI_DOUBLE, to, tag, MPI_COMM_WORLD);
     printf("%d, wyslal wartosc %f: do procesu %d\n",myid,data,to);
  }

  if(myid != 0)
  {
    MPI_Recv(&data2, 1, MPI_DOUBLE, from, tag,  MPI_COMM_WORLD, &status);
    printf("%d, otrzymal wartosc %f: od procesu %d\n",myid,data2,from);
  }
  
  printf("Dla: %d, wartosc PI to %f: \n",myid,data);

  MPI_Finalize();
  return 0 ;
  }