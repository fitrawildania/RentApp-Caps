package com.myrent.capstoneproject.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import android.widget.Toast
import com.myrent.capstoneproject.R
import com.myrent.capstoneproject.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity(), TransactionFinishedCallback {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari HomeFragment
        val carName = intent.getStringExtra("carName") ?: "N/A"
        val ownerName = intent.getStringExtra("ownerName") ?: "N/A"
        val about = intent.getStringExtra("about") ?: "N/A"
        val spesifikasi = intent.getStringExtra("spesifikasi") ?: "N/A"
        val seatCount = intent.getIntExtra("seatCount", 0)
        val carImageResId = intent.getIntExtra("carImageResId", R.drawable.car)

        // Set data ke tampilan
        binding.tvCarName.text = carName
        binding.tvOwnerName.text = ownerName
        binding.tvAbout.text = about
        binding.tvSeatCount.text = "$seatCount Kursi"
        binding.ivCar.setImageResource(carImageResId)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Initialize Midtrans SDK
        initMidtransSDK()

        // Button rent click
        binding.btnRent.setOnClickListener {
            goToPayment()
        }
    }

    private fun goToPayment() {
        val qty = 1 // Adjust this as necessary
        val harga = 100000.0 // Adjust this as necessary
        val amount = qty * harga

        val transactionRequest = TransactionRequest("Rent-${System.currentTimeMillis()}", amount)
        val detail = ItemDetails("CAR_RENTAL_101", harga, qty, "Car Rental")

        val itemDetails = ArrayList<ItemDetails>()
        itemDetails.add(detail)

        uiKitDetails(transactionRequest)
        transactionRequest.itemDetails = itemDetails

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)

    }

    private fun initMidtransSDK() {
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-pThkV1MH9Akux2UK") //Client_key
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setMerchantBaseUrl("https://bebesimple.go-cow.com/ppresponse.php/charge/")
            .enableLog(true)
            .setLanguage("id")
            .buildSDK()
    }

    override fun onTransactionFinished(transactionResult: TransactionResult?) {
        transactionResult?.let { result ->
            if (result.response != null) {
                when (result.status) {
                    TransactionResult.STATUS_SUCCESS -> Toast.makeText(
                        this,
                        "Transaction Finished. ID: " + result.response.transactionId,
                        Toast.LENGTH_LONG
                    ).show()
                    TransactionResult.STATUS_PENDING -> Toast.makeText(
                        this,
                        "Transaction Pending. ID: " + result.response.transactionId,
                        Toast.LENGTH_LONG
                    ).show()
                    TransactionResult.STATUS_FAILED -> Toast.makeText(
                        this,
                        "Transaction Failed. ID: " + result.response.transactionId + ". Message: " + result.response.statusMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (result.isTransactionCanceled) {
                Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show()
            } else {
                if (result.status.equals(TransactionResult.STATUS_INVALID, true)) {
                    Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uiKitDetails(transactionRequest: TransactionRequest) {
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "Customer123"
        customerDetails.phone = "08123456789"
        customerDetails.firstName = "John"
        customerDetails.lastName = "Doe"
        customerDetails.email = "john.doe@example.com"

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "123 Main St"
        shippingAddress.city = "Jakarta"
        shippingAddress.postalCode = "12345"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "123 Main St"
        billingAddress.city = "Jakarta"
        billingAddress.postalCode = "12345"
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails
    }


}
