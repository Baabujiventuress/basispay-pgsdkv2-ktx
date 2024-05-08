package com.basispaypg
/**
 * @author Vinoth
 * Published By  BasisPay
 * Modified on 08-MAY-2024
 */
class BasisPayPaymentParams {
    var apiKey: String? = null
    var secureHash: String? = null
    var orderReference: String? = null
    var customerName: String? = null
    var customerEmail: String? = null
    var customerMobile: String? = null
    var address: String? = null
    var postalCode: String? = null
    var city: String? = null
    var region: String? = null
    var country: String? = null

    var deliveryAddress: String? = null
    var deliveryCustomerName: String? = null
    var deliveryCustomerMobile: String? = null
    var deliveryPostalCode: String? = null
    var deliveryCity: String? = null
    var deliveryRegion: String? = null
    var deliveryCountry: String? = null

    fun BasisPayPaymentParams() {}

    @JvmName("apiKey")
    fun getApiKey(): String? {
        return apiKey
    }

    @JvmName("apiKey")
    fun setApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    @JvmName("secureHash")
    fun getSecureHash(): String? {
        return secureHash
    }

    @JvmName("secureHash")
    fun setSecureHash(secureHash: String?) {
        this.secureHash = secureHash
    }

    @JvmName("orderReference")
    fun getOrderReference(): String? {
        return orderReference
    }

    @JvmName("orderReference")
    fun setOrderReference(orderReference: String?) {
        this.orderReference = orderReference
    }

    @JvmName("customerName")
    fun getCustomerName(): String? {
        return customerName
    }

    @JvmName("customerName")
    fun setCustomerName(customerName: String?) {
        this.customerName = customerName
    }

    @JvmName("customerEmail")
    fun getCustomerEmail(): String? {
        return customerEmail
    }

    @JvmName("customerEmail")
    fun setCustomerEmail(customerEmail: String?) {
        this.customerEmail = customerEmail
    }

    @JvmName("customerMobile")
    fun getCustomerMobile(): String? {
        return customerMobile
    }

    @JvmName("customerMobile")
    fun setCustomerMobile(customerMobile: String?) {
        this.customerMobile = customerMobile
    }

    @JvmName("address")
    fun getAddress(): String? {
        return address
    }

    @JvmName("address")
    fun setAddress(address: String?) {
        this.address = address
    }

    @JvmName("postalCode")
    fun getPostalCode(): String? {
        return postalCode
    }

    @JvmName("postalCode")
    fun setPostalCode(postalCode: String?) {
        this.postalCode = postalCode
    }

    @JvmName("city")
    fun getCity(): String? {
        return city
    }

    @JvmName("city")
    fun setCity(city: String?) {
        this.city = city
    }

    @JvmName("region")
    fun getRegion(): String? {
        return region
    }

    @JvmName("region")
    fun setRegion(region: String?) {
        this.region = region
    }

    @JvmName("country")
    fun getCountry(): String? {
        return country
    }

    @JvmName("country")
    fun setCountry(country: String?) {
        this.country = country
    }

    @JvmName("deliveryAddress")
    fun getDeliveryAddress(): String? {
        return deliveryAddress
    }

    @JvmName("deliveryAddress")
    fun setDeliveryAddress(deliveryAddress: String?) {
        this.deliveryAddress = deliveryAddress
    }

    @JvmName("deliveryName")
    fun getDeliveryCustomerName(): String? {
        return deliveryCustomerName
    }

    @JvmName("deliveryName")
    fun setDeliveryCustomerName(deliveryCustomerName: String?) {
        this.deliveryCustomerName = deliveryCustomerName
    }

    @JvmName("deliveryMobile")
    fun getDeliveryCustomerMobile(): String? {
        return deliveryCustomerMobile
    }

    @JvmName("deliveryMobile")
    fun setDeliveryCustomerMobile(deliveryCustomerMobile: String?) {
        this.deliveryCustomerMobile = deliveryCustomerMobile
    }

    @JvmName("deliveryPostalCode")
    fun getDeliveryPostalCode(): String? {
        return deliveryPostalCode
    }

    @JvmName("deliveryPostalCode")
    fun setDeliveryPostalCode(deliveryPostalCode: String?) {
        this.deliveryPostalCode = deliveryPostalCode
    }

    @JvmName("deliveryCity")
    fun getDeliveryCity(): String? {
        return deliveryCity
    }

    @JvmName("deliveryCity")
    fun setDeliveryCity(deliveryCity: String?) {
        this.deliveryCity = deliveryCity
    }

    @JvmName("deliveryRegion")
    fun getDeliveryRegion(): String? {
        return deliveryRegion
    }

    @JvmName("deliveryRegion")
    fun setDeliveryRegion(deliveryRegion: String?) {
        this.deliveryRegion = deliveryRegion
    }

    @JvmName("deliveryCountry")
    fun getDeliveryCountry(): String? {
        return deliveryCountry
    }

    @JvmName("deliveryCountry")
    fun setDeliveryCountry(deliveryCountry: String?) {
        this.deliveryCountry = deliveryCountry
    }
}