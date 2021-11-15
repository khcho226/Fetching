from flask import Flask, render_template, send_file
from flask import request
import flask
import os
import return_color
import sys
from PIL import Image

from werkzeug.utils import secure_filename
app=Flask(__name__)



@app.route('/', methods = ["POST"])
def hello_world():
    if request.method == 'POST':
        print(flask.request.files)
        print(flask.request.files.get('image'))
        # print(flask.request.files['20200912_174631.jpg'])
        print(flask.request.form)

        file_dir2 = "C:\\Users\\kim01\\MyWorkload\\photo.png"
        a = return_color.result(file_dir2)

        file_dir = "C:\\Users\\kim01\\MyWorkload\\img!.jpg"
        f2 = flask.request.files.get('image')
        f2.save(file_dir)
        

        #print(a)

        
        
        #return a
        return send_file(file_dir, mimetype='image/jpg')
        

@app.route('/one')
def hello_one():
    return 'Hello One'


if __name__ == "__main__":
    app.run(host='0.0.0.0')
