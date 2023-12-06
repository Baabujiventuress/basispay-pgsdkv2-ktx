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
        pgPaymentParams.setApiKey("Api Key") //required field(*)
        pgPaymentParams.setSecureHash("Secure Hash") //required field(*)
        pgPaymentParams.setOrderReference("Order Reference") //required field(*)
        pgPaymentParams.setCustomerName("Customer Name") //required field(*)
        pgPaymentParams.setCustomerEmail("Customer Email") //required field(*)
        pgPaymentParams.setCustomerMobile("Customer Mobile") //required field(*)
        pgPaymentParams.setAddress("Address") //required field(*)
        pgPaymentParams.setPostalCode("PostalCode") //required field(*)
        pgPaymentParams.setCity("City") //required field(*)
        pgPaymentParams.setRegion("Region") //required field(*)
        pgPaymentParams.setCountry("IND") //required field(*)


        //// optional parameters
        pgPaymentParams.setDeliveryAddress("Delivery Address")
        pgPaymentParams.setDeliveryCustomerName("Delivery Customer Name")
        pgPaymentParams.setDeliveryCustomerMobile("Delivery Customer Mobile")
        pgPaymentParams.setDeliveryPostalCode("Delivery Postal Code")
        pgPaymentParams.setDeliveryCity("Delivery City")
        pgPaymentParams.setDeliveryRegion("Delivery Region")
        pgPaymentParams.setDeliveryCountry("IND")

        val pgPaymentInitializer =
            BasisPayPaymentInitializer(pgPaymentParams, this@MainActivity,
//                response.getString(Const.RETURN_URL),
                "Your Return URL",
                false) //TEST = false or LIVE = true
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
                        val referenceNo = response.getString("referenceNo")
                        val success = response.getBoolean("success")

//                        binding.tv1.text = "Reference No: $referenceNo"
//                        binding.tv2.text = "Is Success: $success"

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