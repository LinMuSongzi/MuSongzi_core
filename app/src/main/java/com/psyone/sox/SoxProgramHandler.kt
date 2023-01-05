package com.psyone.sox

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioCapabilities
import com.google.android.exoplayer2.audio.AudioSink
import com.google.android.exoplayer2.audio.DefaultAudioSink
import com.musongzi.core.base.vm.DataDriveViewModel
import java.io.File

object SoxProgramHandler {

    init {
        System.loadLibrary("ColSleep_sox_lib")
    }

    const val TAG = "SOX"

    @JvmStatic
    external fun exampleConvertByPcmData(byteArray: ByteArray, values: String?): ByteArray


    @RequiresApi(Build.VERSION_CODES.M)
    fun exoPlaySImple(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        //请填写本地/网络的 wav文件
        path: String?
    ): Player? {

//        var ouputPlayFile: File? = null
        if (path == null) {
            Toast.makeText(context, "不存在播放文件", Toast.LENGTH_SHORT).show()
            return null
        }

        //1. 创建播放器
        val player = ExoPlayer.Builder(context).setRenderersFactory(object :
            DefaultRenderersFactory(context) {
            override fun buildAudioSink(
                context: Context,
                enableFloatOutput: Boolean,
                enableAudioTrackPlaybackParams: Boolean,
                enableOffload: Boolean
            ): AudioSink? {
                return DefaultAudioSink.Builder()
                    .setAudioCapabilities(AudioCapabilities.getCapabilities(context))
                    .setEnableFloatOutput(enableFloatOutput)
                    .setEnableAudioTrackPlaybackParams(enableAudioTrackPlaybackParams)
                    /**
                     * 设置SoxAudioProcessor
                     * 处理音频数据
                     */
                    .setAudioProcessors(arrayOf(SoxAudioProcessor()))
                    .setOffloadMode(
                        if (enableOffload) DefaultAudioSink.OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED else DefaultAudioSink.OFFLOAD_MODE_DISABLED
                    )
                    .build()
            }
        }).build()

        Log.i(TAG, "exoPlaySImple: $path")
        player.setMediaItem(MediaItem.fromUri(path))

        //4.当Player处于STATE_READY状态时，进行播放
        player.playWhenReady = true

        //5. 调用prepare开始加载准备数据，该方法时异步方法，不会阻塞ui线程
        player.prepare()
        player.play() //  此时处于 STATE_BUFFERING = 2;
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
//                player.pause()
                player.stop()
                player.release()
            }
        })

        return player;

    }

}