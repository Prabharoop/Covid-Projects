package com.example.test_covid

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.sql.Array
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class Classifier(assetManager : AssetManager, modelPath : String, labelPath : String, inputSize : Int) {
    private var interpreter : Interpreter
    private var labelList : List<String>
    private val inputSize : Int = inputSize
    private val pixelSize : Int = 3
    private val imageMean = 0
    private val imageSTD = 255.0f
    private val maxResults = 1
    private val threshold = 0.5f

    data class Recognition(
        var id : String = "",
        var title : String = "",
        var confidence : Float = 0f
    ){
        override fun toString(): String {
            return "Title : $title, Confidence : $confidence"
        }

    }

    init {
        val options = Interpreter.Options()
        options.setNumThreads(5)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assetManager,modelPath),options)
        labelList = loadLabelList(assetManager,labelPath)
    }

    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset, declaredLength)
    }

    fun recognizeImage(bitmap: Bitmap): kotlin.Array<FloatArray> {
        val scalesBitmap = Bitmap.createScaledBitmap(bitmap,inputSize,inputSize, false)
        val byteBuffer = convertBitMapToByteBuffer(scalesBitmap)
        val result = Array(1) { FloatArray(labelList.size) }
        interpreter.run(byteBuffer,result)
        Log.d("classifer",result[0][0].toString())
        return result
    }

//    private fun getResults(labelProbArray: kotlin.Array<FloatArray>): List<Classifier.Recognition> {
//        val recognition = java.util.ArrayList<Recognition>()
//        for (i in labelList.indices) {
//            val confidence = labelProbArray[0][i]
//            if (confidence >= threshold) {
//
//            }
//        }
//
//    }


    private fun convertBitMapToByteBuffer(bitmap:Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * pixelSize)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(inputSize * inputSize)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val input = intValues[pixel++]

                byteBuffer.putFloat((((input.shr(16)  and 0xFF) - imageMean) / imageSTD))
                byteBuffer.putFloat((((input.shr(8) and 0xFF) - imageMean) / imageSTD))
                byteBuffer.putFloat((((input and 0xFF) - imageMean) / imageSTD))
            }
        }
        return byteBuffer

    }
//    private fun getSortedResult(labelProbArray: kotlin.Array<FloatArray>): List<Classifier.Recognition> {
//        Log.d("Classifier", "List Size:(%d, %d, %d)".format(labelProbArray.size,labelProbArray[0].size,labelList.size))
//
//        val pq = PriorityQueue(
//            maxResults,
//            Comparator<Classifier.Recognition> {
//                    (_, _, confidence1), (_, _, confidence2)
//                -> java.lang.Float.compare(confidence1, confidence2) * -1
//            })
//
//        for (i in labelList.indices) {
//            val confidence = labelProbArray[0][i]
//            if (confidence >= threshold) {
//                pq.add(Classifier.Recognition("" + i,
//                    if (labelList.size > i) labelList[i] else "Unknown", confidence)
//                )
//            }
//        }
//        Log.d("Classifier", "pqsize:(%d)".format(pq.size))
//
//        val recognitions = java.util.ArrayList<Recognition>()
//        val recognitionsSize = Math.min(pq.size, maxResults)
//        for (i in 0 until recognitionsSize) {
//            recognitions.add(pq.poll())
//        }
//        return recognitions
//    }
}