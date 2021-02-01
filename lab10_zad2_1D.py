import tensorflow as tf
import numpy as np
from tensorflow import keras

def funkcja(x):
    return (5*x)-6

x=[]
y=[]

for i in range(-10,10):
  x.append(i)
  y.append(funkcja(i))

model = tf.keras.Sequential([keras.layers.Dense(units=1, input_shape=[1])])

model.compile(optimizer='sgd', loss='mean_squared_error')

xs = np.array(x, dtype=float)
ys = np.array(y, dtype=float)

model.fit(xs, ys, epochs=500)

print("Wynik sieci dla 11: "+str(model.predict([11.0])))
print("Wynik funkcji dla 11: " + str(funkcja(11.0)))