package com.nitamlII.heart_uasii

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_app)

        val enterButton: Button = findViewById(R.id.enterButton)

        enterButton.setOnClickListener {
            // Intent untuk berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Menutup IntroActivity agar tidak kembali ke sini saat tombol back ditekan
            finish()
        }
        supportActionBar?.hide()
    }
}
