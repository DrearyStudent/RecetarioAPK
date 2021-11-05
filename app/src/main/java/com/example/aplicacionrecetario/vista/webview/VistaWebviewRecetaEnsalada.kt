package com.example.aplicacionrecetario.vista.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.aplicacionrecetario.R
import java.lang.NullPointerException

class VistaWebviewRecetaEnsalada : AppCompatActivity() {
    lateinit var webViewEnsalada: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_webview_receta_ensalada)

        try {
            setWebView()
        } catch (e: NullPointerException) {
            Toast.makeText(this@VistaWebviewRecetaEnsalada, "Ocurrió un error inesperado, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWebView() {
        webViewEnsalada = findViewById(R.id.webViewEnsalada)
        webViewEnsalada.webViewClient = WebViewClient()
        webViewEnsalada.loadUrl("https://www.recetasamc.info/recetasamc/categoria/ensaladas")
    }

    override fun onBackPressed() {
        if (webViewEnsalada.canGoBack()) {
            webViewEnsalada.goBack()
        } else {
            super.onBackPressed()
        }
    }
}