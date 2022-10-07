package com.basispaypg

class BasisPayPaymentParams {
    private var apiKey: String? = null
    private var secureHash: String? = null
    private var orderReference: String? = null
    private var customerName: String? = null
    private var customerEmail: String? = null
    private var customerMobile: String? = null
    private var address: String? = null
    private var postalCode: String? = null
    private var city: String? = null
    private var region: String? = null
    private var country: String? = null

    private var deliveryAddress: String? = null
    private var deliveryCustomerName: String? = null
    private var deliveryCustomerMobile: String? = null
    private var deliveryPostalCode: String? = null
    private var deliveryCity: String? = null
    private var deliveryRegion: String? = null
    private var deliveryCountry: String? = null

    fun BasisPayPaymentParams() {}


    fun getApiKey(): String? {
        return apiKey
    }

    fun setApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    fun getSecureHash(): String? {
        return secureHash
    }

    fun setSecureHash(secureHash: String?) {
        this.secureHash = secureHash
    }

    fun getOrderReference(): String? {
        return orderReference
    }

    fun setOrderReference(orderReference: String?) {
        this.orderReference = orderReference
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String?) {
        this.customerName = customerName
    }

    fun getCustomerEmail(): String? {
        return customerEmail
    }

    fun setCustomerEmail(customerEmail: String?) {
        this.customerEmail = customerEmail
    }

    fun getCustomerMobile(): String? {
        return customerMobile
    }

    fun setCustomerMobile(customerMobile: String?) {
        this.customerMobile = customerMobile
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getPostalCode(): String? {
        return postalCode
    }

    fun setPostalCode(postalCode: String?) {
        this.postalCode = postalCode
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getRegion(): String? {
        return region
    }

    fun setRegion(region: String?) {
        this.region = region
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getDeliveryAddress(): String? {
        return deliveryAddress
    }

    fun setDeliveryAddress(deliveryAddress: String?) {
        this.deliveryAddress = deliveryAddress
    }

    fun getDeliveryCustomerName(): String? {
        return deliveryCustomerName
    }

    fun setDeliveryCustomerName(deliveryCustomerName: String?) {
        this.deliveryCustomerName = deliveryCustomerName
    }

    fun getDeliveryCustomerMobile(): String? {
        return deliveryCustomerMobile
    }

    fun setDeliveryCustomerMobile(deliveryCustomerMobile: String?) {
        this.deliveryCustomerMobile = deliveryCustomerMobile
    }

    fun getDeliveryPostalCode(): String? {
        return deliveryPostalCode
    }

    fun setDeliveryPostalCode(deliveryPostalCode: String?) {
        this.deliveryPostalCode = deliveryPostalCode
    }

    fun getDeliveryCity(): String? {
        return deliveryCity
    }

    fun setDeliveryCity(deliveryCity: String?) {
        this.deliveryCity = deliveryCity
    }

    fun getDeliveryRegion(): String? {
        return deliveryRegion
    }

    fun setDeliveryRegion(deliveryRegion: String?) {
        this.deliveryRegion = deliveryRegion
    }

    fun getDeliveryCountry(): String? {
        return deliveryCountry
    }

    fun setDeliveryCountry(deliveryCountry: String?) {
        this.deliveryCountry = deliveryCountry
    }
}