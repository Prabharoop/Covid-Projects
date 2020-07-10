package com.example.test_covid

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){


    private val mInputSize = 96
    private val mModelPath = "init_model.tflite"
    private val mLabelPath = "labels.txt"
    private lateinit var classifier: Classifier
    lateinit var filpath : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClassifier()
//        initViews() "@mipmap/ic_launcher"
        buttonChoose.setOnClickListener {
            startFileChooser()
        }
        buttonUpload.setOnClickListener {
            predictImage()
        }
    }


    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose Picture"),111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filpath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filpath)
            images.setImageBitmap(bitmap)
        }
    }
    private fun initClassifier() {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
    }
    private fun predictImage() {
        val bitmap = (images.getDrawable() as BitmapDrawable).bitmap
        val result = classifier.recognizeImage(bitmap)
        if(result[0][0] < 0.5f){
            textView.text = "Result : Not Covid"
        }
        if(result[0][0] > 0.5f){
            var class1 = result[0][0].toString()
            textView.text = "Result: Covid , Confidence : $class1"
        }
    }
//
//    private fun initViews() {
//        findViewById<ImageView>(R.id.iv_1).setOnClickListener(this)
//        findViewById<ImageView>(R.id.iv_2).setOnClickListener(this)
//    }
//
//    override fun onClick(view: View?) {
//        val bitmap = ((view as ImageView).drawable as BitmapDrawable).bitmap
//
//        val result = classifier.recognizeImage(bitmap)
//
//        if (result[0][0]>0.5f){
//            runOnUiThread { Toast.makeText(this, "Covid Positive", Toast.LENGTH_SHORT).show() }
//        }
//        else{
//            runOnUiThread { Toast.makeText(this, "Covid Negetive", Toast.LENGTH_SHORT).show() }
//        }
//
////        runOnUiThread { Toast.makeText(this, result.get(0).title, Toast.LENGTH_SHORT).show() }
//    }
}
