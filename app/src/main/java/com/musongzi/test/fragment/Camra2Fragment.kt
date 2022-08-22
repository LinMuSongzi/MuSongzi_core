package com.musongzi.test.fragment

import android.media.ImageReader
import android.net.Uri
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.musongzi.core.base.fragment.MszFragment
import com.musongzi.test.databinding.FragmentCamera2Binding
import com.musongzi.test.databinding.FragmentMainIndexBinding
import com.musongzi.test.vm.TestViewModel
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/*** created by linhui * on 2022/8/18 */
class Camra2Fragment: MszFragment<TestViewModel, FragmentCamera2Binding>() {

    var flag = 0
    var executor = Executor {
        requireActivity().runOnUiThread {
            it.run()
        }
    }
    var camera:Camera? = null

    override fun initView() {
        dataBinding.take.setOnClickListener {
            if(flag == 1){
                takePhoto()
            }else if(flag == 0) {
                startCameraPreview()
            }
        }
//        dataBinding.surfacePreview.controller.p

    }

    private fun takePhoto() {
//        camera?.
//        dataBinding.surfacePreview.controller?.takePicture(executor,object : ImageCapture.OnImageCapturedCallback(){
//            override fun onCaptureSuccess(image: ImageProxy) {
//                image.image?.
////                image.planes.
//            }
//        })
    }

    override fun initEvent() {
//        TODO("Not yet implemented")
    }

    override fun initData() {
//        TODO("Not yet implemented")
    }

    /**
     * 开始相机预览
     */
    private fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            //用于将相机的生命周期绑定到生命周期所有者
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            //创建预览
            val preview = Preview.Builder().build()
//            preview.
            //选择后置摄像头
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
            try {
                //在重新绑定之前取消绑定
                cameraProvider.unbindAll()
                //将生命周期,选择摄像头,预览,绑定到相机
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview)
                printlnCamera(camera)
//                camera.cameraControl
                //设置预览的View
                preview.setSurfaceProvider(dataBinding.surfacePreview.surfaceProvider)
                this.camera = camera
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun printlnCamera(camera: Camera) {
        Log.i(TAG, "cameraState: ${camera.cameraInfo.cameraState}")
        Log.i(TAG, "implementationType: ${camera.cameraInfo.implementationType}")
        Log.i(TAG, "isYuvReprocessingSupported: ${camera.cameraInfo.isYuvReprocessingSupported}")
//        Log.i(TAG, "isFocusMeteringSupported: ${camera.cameraInfo.isFocusMeteringSupported}")
        Log.i(TAG, "isPrivateReprocessingSupported: ${camera.cameraInfo.isPrivateReprocessingSupported}")
        Log.i(TAG, "printlnCamera: ${camera.cameraInfo.zoomState}")
    }

}