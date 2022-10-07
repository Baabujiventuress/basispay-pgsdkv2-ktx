package com.basispaypg

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

class BasisPayPaymentInitializer constructor(paymentParams:BasisPayPaymentParams, activity:Activity, sReturnUrl:String){
    private var context: Activity? = null
    private val params: HashMap<String?, String?> = LinkedHashMap<String?, String?>()
    private var returnUrl: String? = null

    init {
        context = activity
        returnUrl = sReturnUrl

        if (TextUtils.isEmpty(paymentParams.getApiKey())) {
            throw RuntimeException("ApiKey missing")
        } else {
            params["apiKey"] = paymentParams.getApiKey()
            if (TextUtils.isEmpty(paymentParams.getSecureHash())) {
                throw java.lang.RuntimeException("SecureHash missing")
            } else {
                params["secureHash"] = paymentParams.getSecureHash()
                if (TextUtils.isEmpty(paymentParams.getOrderReference())) {
                    throw java.lang.RuntimeException("Order Reference missing")
                } else {
                    params["orderReference"] = paymentParams.getOrderReference()
                    if (TextUtils.isEmpty(paymentParams.getCustomerName())) {
                        throw java.lang.RuntimeException("Customer Name missing")
                    } else {
                        params["customerName"] = paymentParams.getCustomerName()
                        if (TextUtils.isEmpty(paymentParams.getCustomerEmail())) {
                            throw java.lang.RuntimeException("customer Email missing")
                        } else {
                            params["customerEmail"] = paymentParams.getCustomerEmail()
                            if (TextUtils.isEmpty(paymentParams.getCustomerMobile())) {
                                throw java.lang.RuntimeException("customerMobile missing")
                            } else {
                                params["customerMobile"] = paymentParams.getCustomerMobile()
                                if (TextUtils.isEmpty(paymentParams.getAddress())) {
                                    throw java.lang.RuntimeException("address missing")
                                } else {
                                    params["address"] = paymentParams.getAddress()
                                    if (TextUtils.isEmpty(paymentParams.getPostalCode())) {
                                        throw java.lang.RuntimeException("postalCode missing")
                                    } else {
                                        params["postalCode"] = paymentParams.getPostalCode()
                                        if (TextUtils.isEmpty(paymentParams.getCity())) {
                                            throw java.lang.RuntimeException("city missing")
                                        } else {
                                            params["city"] = paymentParams.getCity()
                                            if (TextUtils.isEmpty(paymentParams.getRegion())) {
                                                throw java.lang.RuntimeException("region missing")
                                            } else {
                                                params["region"] = paymentParams.getRegion()
                                                if (TextUtils.isEmpty(paymentParams.getCity())) {
                                                    throw java.lang.RuntimeException("City missing")
                                                } else {
                                                    params["city"] = paymentParams.getCity()
                                                    if (TextUtils.isEmpty(paymentParams.getCountry())) {
                                                        throw java.lang.RuntimeException("country missing")
                                                    } else {
                                                        params["country"] =
                                                            paymentParams.getCountry()
                                                        if (paymentParams.getDeliveryAddress() != null) {
                                                            params["deliveryAddress"] =
                                                                paymentParams.getDeliveryAddress()
                                                            if (paymentParams.getDeliveryCustomerName() != null) {
                                                                params["deliveryName"] =
                                                                    paymentParams.getDeliveryCustomerName()
                                                                if (paymentParams.getDeliveryCustomerMobile() != null) {
                                                                    params["deliveryMobile"] =
                                                                        paymentParams.getDeliveryCustomerMobile()
                                                                    if (paymentParams.getDeliveryPostalCode() != null) {
                                                                        params["deliveryPostalCode"] =
                                                                            paymentParams.getDeliveryPostalCode()
                                                                        if (paymentParams.getDeliveryCity() != null) {
                                                                            params["deliveryCity"] =
                                                                                paymentParams.getDeliveryCity()
                                                                            if (paymentParams.getDeliveryRegion() != null) {
                                                                                params["deliveryRegion"] =
                                                                                    paymentParams.getDeliveryRegion()
                                                                                if (paymentParams.getDeliveryCountry() != null) {
                                                                                    params["deliveryCountry"] =
                                                                                        paymentParams.getDeliveryCountry()
                                                                                } else {
                                                                                    throw java.lang.RuntimeException(
                                                                                        "delivery country missing"
                                                                                    )
                                                                                }
                                                                            } else {
                                                                                throw java.lang.RuntimeException(
                                                                                    "delivery region missing"
                                                                                )
                                                                            }
                                                                        } else {
                                                                            throw java.lang.RuntimeException(
                                                                                "delivery city missing"
                                                                            )
                                                                        }
                                                                    } else {
                                                                        throw java.lang.RuntimeException(
                                                                            "delivery postalCode missing"
                                                                        )
                                                                    }
                                                                } else {
                                                                    throw java.lang.RuntimeException(
                                                                        "delivery customerMobile missing"
                                                                    )
                                                                }
                                                            } else {
                                                                throw java.lang.RuntimeException("delivery customerName missing")
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    fun initiatePaymentProcess() {
        val startActivity = Intent(context, BasisPayPaymentActivity::class.java)
        startActivity.putExtra(BasisPayPGConstants.POST_PARAMS, buildParamsForPayment())
        startActivity.putExtra(BasisPayPGConstants.PAYMENT_RETURN_URL, returnUrl)
        startActivity.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        context!!.startActivityForResult(startActivity, BasisPayPGConstants.REQUEST_CODE)
    }

    private fun buildParamsForPayment(): String {
        val params = params
        var hashPostParamsBuilder = StringBuilder()
        var parameterEntry: String
        val sorted = TreeMap<String?, String?>()

        // Copy all data from hashMap into TreeMap
        sorted.putAll(params)

        // Display the TreeMap which is naturally sorted
        try {
            val var3: Iterator<*> = sorted.keys.iterator()
            while (var3.hasNext()) {
                val key = var3.next() as String
                parameterEntry = key + "=" + sorted[key] + "&"
                hashPostParamsBuilder = hashPostParamsBuilder.append(parameterEntry)
            }
        } catch (var6: Exception) {
            val sw = StringWriter()
            var6.printStackTrace(PrintWriter(sw))
            parameterEntry = sw.toString()
            Toast.makeText(context, parameterEntry, Toast.LENGTH_SHORT).show()
        }
        val postParams = hashPostParamsBuilder.toString()
        return if (postParams[postParams.length - 1] == '&') postParams.substring(
            0,
            postParams.length - 1
        ) else postParams
    }
}