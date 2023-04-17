package com.basispaypg

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter

class BasisPayPaymentActivity : AppCompatActivity() {
    var pb: ProgressBar? = null
    private var webView: WebView? = null
    var referenceNo: String? = null
    var success:String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        webView = findViewById(R.id.pgPaymentGatewayWebview)
        pb = findViewById(R.id.progressBar)
        pb!!.visibility = View.VISIBLE

        val postPaymentRequestParams = this.intent.getStringExtra(BasisPayPGConstants.POST_PARAMS)
        val returnUrl = this.intent.getStringExtra(BasisPayPGConstants.PAYMENT_RETURN_URL)
        val paymentUrl = this.intent.getStringExtra(BasisPayPGConstants.PAYMENT_URL)
        println("ReturnUrl===$returnUrl")
        println("Params===$postPaymentRequestParams")

        try {
            webView!!.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    pb!!.visibility = View.GONE
                    Log.i("log", "onPageFinished : $url")
                    runOnUiThread {
                        val checkUrl = paymentUrl+BasisPayPGConstants.CONTAIN_CHECK
                        val appUrl = paymentUrl+BasisPayPGConstants.CONTAIN_RES
                        if (url.contains(checkUrl)) {
                            val s =
                                url.split(appUrl)
                                    .toTypedArray()
                            val mapValue: Map<String, String> =
                                this@BasisPayPaymentActivity.getQueryMap(s[1])
                            println(mapValue["?ref"])
                            println(mapValue["success"])
                            referenceNo = mapValue["?ref"]
                            success = mapValue["success"]
                            println("referenceNo==$referenceNo")
                        }
                    }
                }

                override fun onPageStarted(view: WebView, url: String, facIcon: Bitmap?) {
                    super.onPageStarted(view, url, facIcon)
                    pb!!.visibility = View.VISIBLE
                    Log.i("log", "onPageStarted : $url")
                    runOnUiThread {
                        val pgResponse = JSONObject()
                        if (url.equals(returnUrl, ignoreCase = true)) {
                            try {
                                pgResponse.put("status", "success")
                                pgResponse.put("payment_response", referenceNo)
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
            val postUrl = paymentUrl+BasisPayPGConstants.END_POINT
            Log.d("postUrl", postUrl)
            Log.d("postParamValues", postPaymentRequestParams!!)
            webView!!.postUrl(postUrl, postPaymentRequestParams.toByteArray())
        } catch (var7: Exception) {
            val sw = StringWriter()
            var7.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()
            Toast.makeText(this.baseContext, exceptionAsString, Toast.LENGTH_SHORT).show()
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