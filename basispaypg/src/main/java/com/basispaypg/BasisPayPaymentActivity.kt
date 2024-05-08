package com.basispaypg

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author Vinoth
 * Published By  BasisPay
 * Modified on 08-MAY-2024
 */

class BasisPayPaymentActivity : AppCompatActivity() {
    var pb: ProgressBar? = null
    private var webView: WebView? = null
    var referenceNo: String? = null
    var success: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        webView = findViewById(R.id.pgPaymentGatewayWebview)
        pb = findViewById(R.id.progressBar)
        pb!!.visibility = View.VISIBLE

        val postPaymentRequestParams = this.intent.getStringExtra(BasisPayPGConstants.POST_PARAMS)
        val returnUrl = this.intent.getStringExtra(BasisPayPGConstants.PAYMENT_RETURN_URL)
        val isProduction = this.intent.getBooleanExtra(BasisPayPGConstants.IS_PRODUCTION, false)

        try {
            webView!!.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    pb!!.visibility = View.GONE
                    Log.i("log", "onPageFinished : $url")
                    runOnUiThread {
                        val checkUrl: String
                        val appUrl: String
                        if (isProduction) { //TODO LIVE MODE
                            checkUrl =
                                BasisPayPGConstants.PRODUCTION_URL + BasisPayPGConstants.CONTAIN_CHECK
                            appUrl =
                                BasisPayPGConstants.PRODUCTION_URL + BasisPayPGConstants.CONTAIN_RES
                        } else { //TODO TEST MODE
                            checkUrl =
                                BasisPayPGConstants.STAGING_URL + BasisPayPGConstants.CONTAIN_CHECK
                            appUrl =
                                BasisPayPGConstants.STAGING_URL + BasisPayPGConstants.CONTAIN_RES
                        }
                        if (url.contains(checkUrl)) {
                            val s =
                                url.split(appUrl)
                                    .toTypedArray()
                            val mapValue: Map<String, String> =
                                this@BasisPayPaymentActivity.getQueryMap(s[1])
                            referenceNo = mapValue["?ref"]
                            success = mapValue["success"]
                        }
                    }
                }

                override fun onPageStarted(view: WebView, url: String, facIcon: Bitmap?) {
                    super.onPageStarted(view, url, facIcon)
                    pb!!.visibility = View.GONE
                    Log.i("log", "onPageStarted : $url")
                    runOnUiThread {
                        val pgResponse = JSONObject()
                        if (url.equals(returnUrl, ignoreCase = true)) {
                            try {
                                pgResponse.put("referenceNo", referenceNo)
                                pgResponse.put("success", success)
                                val paymentResponseCallBackIntent = Intent()
                                paymentResponseCallBackIntent.putExtra(
                                    BasisPayPGConstants.PAYMENT_RESPONSE,
                                    pgResponse.toString()
                                )
                                this@BasisPayPaymentActivity.setResult(
                                    -1,
                                    paymentResponseCallBackIntent
                                )
                                finish()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    val builder = AlertDialog.Builder(this@BasisPayPaymentActivity)
                    var message = when (error!!.primaryError) {
                        SslError.SSL_EXPIRED -> "The certificate has expired."
                        SslError.SSL_IDMISMATCH -> "The certificate Hostname mismatch."
                        SslError.SSL_UNTRUSTED -> "The certificate authority is not trusted."
                        SslError.SSL_DATE_INVALID -> "The certificate date is invalid."
                        SslError.SSL_NOTYETVALID -> "The certificate is not yet valid."
                        else -> "Unknown SSL error."
                    }

                    builder.setPositiveButton(
                        "Continue"
                    ) { dialog, which ->
                        dialog.dismiss()
                        handler!!.proceed()
                    }
                    builder.setNegativeButton(
                        "Cancel"
                    ) { dialog, which ->
                        dialog.dismiss()
                        handler!!.cancel()
                        //Cancel Transaction
                        cancelTransaction()
                    }
                    message += " Do you want to continue anyway?"
                    builder.setTitle("Transaction Alert")
                    builder.setMessage("Do you want to proceed transaction?")
                    val dialog = builder.create()
                    dialog.show()
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    var url = request!!.url.toString()
                    if (url.startsWith("http://")) {
                        try {
                            //change protocol of url string
                            url = url.replace("http://", "https://")

                            view!!.loadUrl(url)
                            return super.shouldOverrideUrlLoading(view, request)
                        } catch (e: java.lang.Exception) {
                            //an error occurred
                            e.printStackTrace()
                        }
                    }
                    return false
                }
            }
            val webSettings = webView!!.settings
            webSettings.javaScriptEnabled = true
            webView!!.settings.domStorageEnabled = true
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            webSettings.domStorageEnabled = true
            webView!!.clearHistory()
            webView!!.clearCache(true)
            webView!!.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(
                    view: WebView,
                    url: String,
                    message: String,
                    result: JsResult
                ): Boolean {
                    return super.onJsAlert(view, url, message, result)
                }

            }

            val postUrl: String?
            if (isProduction) {
                //TODO LIVE MODE
                postUrl = BasisPayPGConstants.PRODUCTION_URL + BasisPayPGConstants.END_POINT
                Log.d("Production PostUrl", postUrl)
                Log.d("postParamValues", postPaymentRequestParams!!)
                webView!!.postUrl(postUrl, postPaymentRequestParams.toByteArray())
            } else {
                //TODO TEST MODE
                postUrl = BasisPayPGConstants.STAGING_URL + BasisPayPGConstants.END_POINT
                Log.d("Staging PostUrl", postUrl)
                Log.d("postParamValues", postPaymentRequestParams!!)
                webView!!.postUrl(postUrl, postPaymentRequestParams.toByteArray())
            }

        } catch (var7: Exception) {
            val sw = StringWriter()
            var7.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()
            Toast.makeText(this.baseContext, exceptionAsString, Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelTransaction() {
        runOnUiThread {
            val pgResponse = JSONObject()
            try {
                pgResponse.put("referenceNo", "TRANSACTION CANCELLED!")
                pgResponse.put("success", "false")
                val paymentResponseCallBackIntent = Intent()
                paymentResponseCallBackIntent.putExtra(
                    BasisPayPGConstants.PAYMENT_RESPONSE,
                    pgResponse.toString()
                )
                this@BasisPayPaymentActivity.setResult(
                    -1,
                    paymentResponseCallBackIntent
                )
                finish()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    fun getQueryMap(query: String): Map<String, String> {
        val params = query.split("&").toTypedArray()
        val map: MutableMap<String, String> = HashMap()
        for (param in params) {
            val name = param.split("=").toTypedArray()[0]
            val value = param.split("=").toTypedArray()[1]
            map[name] = value
        }
        return map
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == 0) {
            when (keyCode) {
                4 -> {
                    if (webView!!.canGoBack()) {
                        webView!!.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView!!.visibility == 0) {
            if (webView!!.canGoBack()) {
                webView!!.goBack()
            }
        } else {
            super.onBackPressed()
        }
    }
}