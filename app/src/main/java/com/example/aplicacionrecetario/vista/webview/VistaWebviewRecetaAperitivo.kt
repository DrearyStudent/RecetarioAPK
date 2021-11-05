package com.example.aplicacionrecetario.vista.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.aplicacionrecetario.R
import java.lang.NullPointerException

class VistaWebviewRecetaAperitivo : AppCompatActivity() {
    lateinit var webViewAperitivo: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_webview_receta_aperitivo)

        try {
            setWebView()
        } catch (e: NullPointerException) {
            Toast.makeText(this@VistaWebviewRecetaAperitivo, "Ocurrió un error inesperado, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWebView() {
        webViewAperitivo = findViewById(R.id.webViewAperitivo)
        webViewAperitivo.webViewClient = WebViewClient()
        webViewAperitivo.loadUrl("https://www.recetasamc.info/recetasamc/categoria/aperitivo")
    }

    override fun onBackPressed() {
        if (webViewAperitivo.canGoBack()) {
            webViewAperitivo.goBack()
        } else {
            super.onBackPressed()
        }
    }
}