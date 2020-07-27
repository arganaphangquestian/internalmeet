package com.rizkydian.internalmeet.ui.meetdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.rizkydian.internalmeet.R
import com.rizkydian.internalmeet.databinding.ActivityMeetDetailBinding
import com.rizkydian.internalmeet.utils.NetworkState
import com.rizkydian.internalmeet.utils.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_meet_detail.*
import timber.log.Timber

@AndroidEntryPoint
class MeetDetailActivity : AppCompatActivity() {
    private lateinit var meetDetailBinding: ActivityMeetDetailBinding
    private lateinit var meetDetailViewModel: MeetDetailViewModel
    private var id: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        meetDetailViewModel = ViewModelProvider(this).get(MeetDetailViewModel::class.java)
        id = intent.getStringExtra("id")
        setTheme(R.style.AppThemeActionBar)
        supportActionBar?.title = "Detail Meet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        meetDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_meet_detail)
        meetDetailBinding.apply {
            lifecycleOwner = this@MeetDetailActivity
            viewModel = meetDetailViewModel
        }
        if (!id.isNullOrEmpty()) {
            meetDetailViewModel.load(id!!).observe(this, Observer {
                when (it.status) {
                    NetworkState.Status.SUCCESS -> {
                        pb_detail.visibility = View.GONE
                        sv_detail.visibility = View.VISIBLE
                    }
                    NetworkState.Status.FAILED -> {
                        Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                        pb_detail.visibility = View.GONE
                        sv_detail.visibility = View.GONE
                    }
                    NetworkState.Status.RUNNING -> {
                        pb_detail.visibility = View.VISIBLE
                        sv_detail.visibility = View.GONE
                    }
                }
            })
            try {
                val bitMatrix = MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, dpToPx(240), dpToPx(240))
                iv_barcode.load(BarcodeEncoder().createBitmap(bitMatrix)) {
                    allowHardware(false)
                    crossfade(true)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}