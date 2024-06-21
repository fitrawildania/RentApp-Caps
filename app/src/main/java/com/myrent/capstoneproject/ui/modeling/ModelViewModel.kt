package com.myrent.capstoneproject.ui.modeling

import android.content.Context
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import com.myrent.capstoneproject.ml.MatrixFactorizationModel
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ModelViewModel(private val context: Context) : ViewModel() {
    private val model: MatrixFactorizationModel = MatrixFactorizationModel.newInstance(context)

    fun recommend(userId: Int, itemId: Int): Float {
        // Prepare inputs
        val inputUser = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)
        val inputItem = TensorBuffer.createFixedSize(intArrayOf(1), DataType.FLOAT32)

        // Convert int values to float and load into ByteBuffer
        val byteBufferUser = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        byteBufferUser.putFloat(userId.toFloat())
        val byteBufferItem = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        byteBufferItem.putFloat(itemId.toFloat())

        // Load ByteBuffers into TensorBuffers
        inputUser.loadBuffer(byteBufferUser)
        inputItem.loadBuffer(byteBufferItem)

        // Run model inference and get result
        val outputs = model.process(inputUser, inputItem)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        // Return the recommendation score
        return outputFeature0.floatArray[0]
    }

    override fun onCleared() {
        super.onCleared()
        model.close()
    }
}
