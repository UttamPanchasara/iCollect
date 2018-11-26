package com.uttampanchasara.baseprojectkotlin.ui.scanner

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * @since 11/26/2018
 */
class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object {
        val TAG = "ScannerActivity"
        val DATA = "DATA"
        val RESULT_CODE = 1004
    }

    lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.v(TAG, rawResult.text) // Prints scan results
        Log.v(TAG, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        val intent = Intent()
        intent.putExtra(DATA, rawResult.text)
        setResult(RESULT_CODE, intent)
        finish()
    }
}