import numpy as np
from keras.models import Model
from keras.layers import Dense, Activation, Lambda, Input 
import keras.backend as K 
from keras.utils import to_categorical

def funkcja(ip):
    a = ip[1]
    x = ip[0]
    b = ip[2]
    return a*x + b

a = Input(shape=(1,))
b = Input(shape=(1,))
ip = Input(shape=(784,))
x = Dense(32, activation="relu", input_dim=784)(ip)
x = Lambda(funkcja)([x, a, b])
x = Dense(10, activation="softmax")(x)
model = Model(inputs=[ip, a, b], outputs=x)
model.compile(optimizer="adam", loss="categorical_crossentropy", metrics=["accuracy"])

x_s = np.random.randn(1000, 784)
a_s = np.ones((1000,1))
b_s = np.zeros((1000,1))
y_s = np.random.randint(0, 10, size=(1000,))
y_s = to_categorical(y_s, num_classes=10)

model.fit( [x_s, a_s, b_s] , y_s, batch_size=64 , epochs=5)