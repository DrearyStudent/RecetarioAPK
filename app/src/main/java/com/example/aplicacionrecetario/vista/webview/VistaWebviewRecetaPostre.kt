package com.example.aplicacionrecetario.vista.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.aplicacionrecetario.R
import java.lang.NullPointerException

class VistaWebviewRecetaPostre : AppCompatActivity() {
    lateinit var webViewPostre: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_webview_receta_postre)

        try {
            setWebView()
        } catch (e: NullPointerException) {
            Toast.makeText(this@VistaWebviewRecetaPostre, "Ocurrió un error inesperado, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWebView() {
        webViewPostre = findViewById(R.id.webViewPostre)
        webViewPostre.webViewClient = WebViewClient()
        webViewPostre.loadUrl("https://www.recetasamc.info/recetasamc/categoria/postre")
    }

    override fun onBackPressed() {
        if (webViewPostre.canGoBack()) {
            webViewPostre.goBack()
        } else {
            super.onBackPressed()
        }
    }
}