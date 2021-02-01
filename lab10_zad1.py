import sys
import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf

tf.config.run_functions_eagerly

@tf.function
def funkcja(x):
    return x * x

#metoda prostokątów

@tf.function
def prostokatow(a, b, i):
    dx = tf.Variable((b - a) / i,name = "dx")
    i = tf.cast(i, tf.int32)
    zwroc = tf.Variable(0.0,name = "zwroc")

    for x in range(i):
        x = tf.cast(x, tf.float32)
        x = x * dx + a
        zwroc = zwroc + dx * funkcja(x)

    return zwroc

#metoda trapezow

@tf.function
def trapezow(a, b, i):
    dx = tf.Variable((b - a) / i,name = "dx")
    zwroc = tf.Variable(0.0,name = "zwroc")
    i = tf.cast(i, tf.int32)

    for x in range(i):
        x = tf.cast(x, tf.float32)
        x = x * dx + a
        fx1 = funkcja(x)
        x = x + dx
        fx2 = funkcja(x)
        zwroc = zwroc + 0.5 * dx * (fx1 + fx2)
    return zwroc

#metoda simpsona

@tf.function
def simps(a,b,n):
    delta_x = tf.Variable((b-a)/n,name = "delta_x")
    total = funkcja(a) + funkcja(b)
    subtotal_sum_1 = 0
    subtotal_sum_2 = 0
    n = tf.cast(n, tf.int32)
    for i in range(0, n, 2):
        x = a + i * delta_x
        subtotal_sum_1 += funkcja(x)
    # druga suma, pamiętamy że n = 2N
    for i in range(1, n-1, 2):
      x = a + i * delta_x
      subtotal_sum_2 += funkcja(x)
    return delta_x * (total + 4 * subtotal_sum_1 + 2 * subtotal_sum_2) / 3

a = tf.Variable(0.0,name = "a")
b = tf.Variable(100.0,name = "b")
i = tf.Variable(35.0,name = "i")

print(prostokatow(a,b,i))
print(trapezow(a,b,i))
print(simps(a,b,i))