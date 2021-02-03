'''
Algorytm służy rozpoznawaniu twarzy na zdjęciach

Do implementacji ImageAI potrzebujemy:
– Tensorflow
– Numpy
– SciPy
– OpenCV
– Pillow
– Matplotlib
– H5py
– Keras
– ImageAI

Na początku importujemy biblioteki ImageAI, os, oraz zdefiniowanią zmienną,
której celem jest umieszczenie obrazu po analizie w tym samym folderze, w którym zamieściliśmy oryginalne zdjęcie.

Następnie, w pierwszej linii zdefiniowaliśmy klasę obiektu, w drugiej linii ustawiliśmy model type dla RetineNet,
w trzeciej linii ustawiliśmy model path, w czwartej wczytaliśmy model do klasy rozpoznawania obiektu, a później wywołujemy funkcję rozpoznawania obiektu, 
którą wykorzystamy do wykrywania i analizy ścieżki obrazu wejściowego i ścieżkę obrazu wyjściowego.

Za pomocą pętli for sprawdzamy wszystkie wyniki zwrócone przez funkcję detector.detectObjectsFromImage, 
a następnie uzyskujemy nazwę i procentowe prawdopodobieństwo modelu dla każdego obiektu wykrytego na obrazie w drugim wierszu.

'''
from imageai.Detection import ObjectDetection
import os
 
execution_path = os.getcwd()
 
detector = ObjectDetection()
detector.setModelTypeAsRetinaNet()
detector.setModelPath( os.path.join(execution_path , "resnet50_coco_best_v2.0.1.h5"))
detector.loadModel()
detections = detector.detectObjectsFromImage(input_image=os.path.join(execution_path , "image.jpg"), output_image_path=os.path.join(execution_path , "imagenew.jpg"))
 
for eachObject in detections:
    print(eachObject["name"] , " : " , eachObject["percentage_probability"] )