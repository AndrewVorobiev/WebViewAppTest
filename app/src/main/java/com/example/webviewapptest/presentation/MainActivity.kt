package com.example.webviewapptest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.webviewapptest.R
import com.example.webviewapptest.databinding.ActivityMainBinding
import com.example.webviewapptest.network.NetworkStatus

class MainActivity : AppCompatActivity() {

    private lateinit var networkStatus: NetworkStatus
    private lateinit var binding: ActivityMainBinding
    private val myWeb : WebView by lazy{
        binding.webViewGoogle
    }

    companion object {
        const val GOOGLE_URL = "https://www.google.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLoadingWebView()
        if (savedInstanceState != null) {
            myWeb.loadUrl(GOOGLE_URL)
        }
        isLoadingWebView()
    }

    private fun isLoadingWebView() {
        networkStatus = NetworkStatus(application)
        myWeb.webViewClient = WebViewClient()
        networkStatus.observe(this) { CONNECTIVITY_SERVICE ->
            if (CONNECTIVITY_SERVICE) {
                binding.imageViewNoNetwork.visibility = View.GONE
                binding.textViewNoNetwork.visibility = View.GONE
                binding.webViewGoogle.visibility = View.VISIBLE
                myWeb.loadUrl(GOOGLE_URL)

            } else {
                binding.webViewGoogle.visibility = View.GONE
                binding.imageViewNoNetwork.visibility = View.VISIBLE
                binding.textViewNoNetwork.visibility = View.VISIBLE
                Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWeb.canGoBack()) {
            myWeb.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_FORWARD && myWeb.canGoForward()) {
            myWeb.goForward()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        myWeb.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        myWeb.restoreState(savedInstanceState)
    }


}

