import numpy as np
import tensorflow as tf

from tensorflow import keras

img_height = 180
img_width = 180

loaded = keras.models.load_model("C:/Users/khcho/Desktop/fashion_model.h5")
model = loaded
test_path = "C:/Users/khcho/Desktop/TEST5.jpg"
img = keras.preprocessing.image.load_img(test_path, target_size = (img_height, img_width))
img_array = keras.preprocessing.image.img_to_array(img)
img_array = tf.expand_dims(img_array, 0)
predictions = model.predict(img_array)
score = tf.nn.softmax(predictions[0])
class_names = ['avant-garde', 'classic', 'country', 'feminine', 'funk', 'genderless', 'hiphop', 'hippie', 'kisch', 'oriental', 'preppy', 'sporty', 'tomboy', 'western']

print(
    "This image most likely belongs to {} with a {:.2f} percent confidence."
    .format(class_names[np.argmax(score)], 100 * np.max(score))
)
