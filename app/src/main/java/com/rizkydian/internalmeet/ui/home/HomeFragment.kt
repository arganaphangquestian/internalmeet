package com.rizkydian.internalmeet.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.ui.scanner.ScannerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        action()
    }

    private fun action() {
        mb_home.setOnClickListener {
            barcodeScanner()
        }
    }

    private fun barcodeScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.captureActivity = ScannerActivity::class.java
        integrator.setOrientationLocked(false)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scanning QR Code")
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val res = IntentIntegrator.parseActivityResult(resultCode, data)
        if(res != null && res.contents != null) {
            Toast.makeText(this.requireContext(), res.contents, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this.requireContext(), "Scan Failed", Toast.LENGTH_LONG).show()
        }
    }

}