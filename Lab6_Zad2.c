#include <stdio.h>
#include "mpi.h"

double f(double x)
{
  return(x * x);
}

int main ( int argc , char  **argv )
{
  int numprocs, myid;
  double data,data2;
  MPI_Status status;
  MPI_Init(&argc , &argv );
  MPI_Comm_rank(MPI_COMM_WORLD, &myid );
  MPI_Comm_size(MPI_COMM_WORLD, &numprocs );

  int tag = 2010;

  double xp,xk;

  // Poczatek całkowania

  xp = 0;
  
  // Koniec całkowania

  xk = 100;
  double s  = 0;
  double dx = (xk - xp) / numprocs;
  
  if(myid == numprocs - 1)
  {
    s += f(xp + myid * dx);
    MPI_Send(&s, 1, MPI_DOUBLE, myid - 1, tag, MPI_COMM_WORLD);
  }
  else if (myid == 0)
  {
    MPI_Recv(&s, 1, MPI_DOUBLE, 1, tag,  MPI_COMM_WORLD, &status);
    s += f(xp + myid * dx);
    s = (s + (f(xp) + f(xk)) / 2) * dx;

    printf("Wartosc calki: %f\n",s);
  }
  else
  {
    MPI_Recv(&s, 1, MPI_DOUBLE, myid + 1, tag,  MPI_COMM_WORLD, &status);
    s += f(xp + myid * dx);
    MPI_Send(&s, 1, MPI_DOUBLE, myid - 1, tag, MPI_COMM_WORLD);

  }

  MPI_Finalize();

  return 0 ;
  }