package com.example.list

import Grandparent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GrandparentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grandparent_detail)

        // Получаем данные о выбранной бабушке из Intent
        val grandparent = intent.getSerializableExtra("grandparent") as Grandparent

        // Отображаем подробное описание бабушки на экране
        val textViewNameDetail = findViewById<TextView>(R.id.textViewNameDetail)
        val textViewAddressDetail = findViewById<TextView>(R.id.textViewAddressDetail)
        val textViewDescriptionDetail = findViewById<TextView>(R.id.textViewDescriptionDetail)

        textViewNameDetail.text = grandparent.name
        textViewAddressDetail.text = grandparent.address
        textViewDescriptionDetail.text = grandparent.description

        // Здесь вы также можете отобразить другие данные о бабушке, включая изображение, если необходимо.
    }
}

