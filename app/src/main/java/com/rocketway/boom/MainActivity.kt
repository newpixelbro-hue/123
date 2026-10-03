package com.rocketway.boom

import android.app.WallpaperManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWallpaper()
        maxVolume()
        playSound()

        Toast.makeText(this, "ROCKET: обои + звук + громкость", Toast.LENGTH_LONG).show()
    }

    private fun setWallpaper() {
        try {
            val wm = WallpaperManager.getInstance(this)
            val input = resources.openRawResource(R.drawable.wall)
            wm.setStream(input)
            input.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun maxVolume() {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager
        am.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
            0
        )
    }

    private fun playSound() {
        try {
            player = MediaPlayer().apply {
                val afd = assets.openFd("sound.mp3")
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                isLooping = true
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}
