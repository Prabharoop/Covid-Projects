package com.example.covidct

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.example.test_covid.Classifier
import kotlinx.android.synthetic.main.activity_dash_board.*

class dashBoard : AppCompatActivity() {

    private val versionList = ArrayList<Versions>()
    lateinit var filpath : Uri
    private val mInputSize = 96
    private val mModelPath = "init_model.tflite"
    private val mLabelPath = "labels.txt"
    private lateinit var classifier: Classifier
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        init_Classifier()
        initData()
        setRecyclerView()
    }

    private fun init_Classifier() {
        classifier = Classifier(assets,modelPath = mModelPath,labelPath = mLabelPath,inputSize = mInputSize)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filpath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filpath)
        }
    }

    private fun setRecyclerView() {
        val versionsAdapter = VersionsAdapter(versionList)
        recycleView.adapter = versionsAdapter
        recycleView.setHasFixedSize(true)
    }

    private fun initData() {
        versionList.add(
            Versions(
                code = "What will this app do?",
                subText = "This App can help to diagnose novel corona virus from the chest CT images. This project was built only for learning process.",
                button_Text = "Contact The Dev"
            ))
        versionList.add(
            Versions(
                code = "Upload",
                subText = "Use the Below Button to upload your Chest CT Image.",
                button_Text = "Upload"
            ))
        versionList.add(
            Versions(
                code = "Predict",
                subText = "Use the button below to make diagnosis predictions from the input Chest CT",
                button_Text = "Predict"
            ))

    }
}
