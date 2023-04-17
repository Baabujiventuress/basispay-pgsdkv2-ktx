package com.basispaysdk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basispaypg.BasisPayPGConstants
import com.basispaypg.BasisPayPaymentInitializer
import com.basispaypg.BasisPayPaymentParams
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pgPaymentParams = BasisPayPaymentParams()
        pgPaymentParams.setApiKey("YOUR_API_KEY") //required field(*)
        pgPaymentParams.setSecureHash("YOUR_SECURE_HASH") //required field(*)
        pgPaymentParams.setOrderReference("YOUR_REFERENCE_NO") //required field(*)
        pgPaymentParams.setCustomerName("XXXXX") //required field(*)
        pgPaymentParams.setCustomerEmail("XXXXX") //required field(*)
        pgPaymentParams.setCustomerMobile("XXXXX") //required field(*)
        pgPaymentParams.setAddress("XXXXX") //required field(*)
        pgPaymentParams.setPostalCode("XXXX") //required field(*)
        pgPaymentParams.setCity("XXXX") //required field(*)
        pgPaymentParams.setRegion("XXXX") //required field(*)
        pgPaymentParams.setCountry("XXX") //required field(*)

        //// optional parameters
        pgPaymentParams.setDeliveryAddress("XXXX")
        pgPaymentParams.setDeliveryCustomerName("XXXX")
        pgPaymentParams.setDeliveryCustomerMobile("XXXX")
        pgPaymentParams.setDeliveryPostalCode("XXXX")
        pgPaymentParams.setDeliveryCity("XXXX");
        pgPaymentParams.setDeliveryRegion("XXXX")
        pgPaymentParams.setDeliveryCountry("XXX")

        val pgPaymentInitializer =
            BasisPayPaymentInitializer(pgPaymentParams, this@MainActivity,
                "YOUR_RETURN_URL","YOUR_PG_CONNECT_URL") //Ex: YOUR_PG_CONNECT_URL = https://basispay.in/
        pgPaymentInitializer.initiatePaymentProcess()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BasisPayPGConstants.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    val paymentResponse =
                        data!!.getStringExtra(BasisPayPGConstants.PAYMENT_RESPONSE)
                    println(paymentResponse)
                    Log.d("Res", paymentResponse!!)
                    if (paymentResponse == "null") {
                        Toast.makeText(this, "Transaction Error!", Toast.LENGTH_SHORT).show()
                    } else {
                        val response = JSONObject(paymentResponse)
                        Log.d("Res", response.toString())
                        val referenceNo = response.getString("referenceNumber")
                        val success = response.getBoolean("success")

                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                Log.d("Res", "Declined!")
            }
        }
    }
}