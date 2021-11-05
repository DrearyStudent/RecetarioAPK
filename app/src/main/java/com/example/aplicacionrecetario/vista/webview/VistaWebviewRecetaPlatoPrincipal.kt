package com.example.aplicacionrecetario.vista.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.aplicacionrecetario.R
import java.lang.NullPointerException

class VistaWebviewRecetaPlatoPrincipal : AppCompatActivity() {
    lateinit var webViewPlatoPrincipal: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_webview_receta_plato_principal)

        try {
            setWebView()
        } catch (e: NullPointerException) {
            Toast.makeText(this@VistaWebviewRecetaPlatoPrincipal, "Ocurrió un error inesperado, lamentamos las molestias que esto pueda ocacionarle", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWebView() {
        webViewPlatoPrincipal = findViewById(R.id.webViewPlatoPrincipal)
        webViewPlatoPrincipal.webViewClient = WebViewClient()
        webViewPlatoPrincipal.loadUrl("https://www.recetasamc.info/recetasamc/categoria/plato-principal")
    }

    override fun onBackPressed() {
        if (webViewPlatoPrincipal.canGoBack()) {
            webViewPlatoPrincipal.goBack()
        } else {
            super.onBackPressed()
        }
    }
}