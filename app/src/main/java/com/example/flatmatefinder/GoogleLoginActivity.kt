package com.example.flatmatefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class GoogleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        val webView = findViewById<WebView>(R.id.webView)

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl("https://flatmate-backend.vercel.app/google")
    }

}