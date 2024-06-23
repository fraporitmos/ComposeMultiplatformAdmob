package org.fraporitmos.englishtenses

import App
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.fraporitmos.englishtenses.MainActivity.Companion.minterstitialAd
import java.net.URLEncoder
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : ComponentActivity() {
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    companion object {
        var minterstitialAd: InterstitialAd? = null
        private var currentActivity: MainActivity? = null

        fun getCurrentActivity(): MainActivity? {
            return currentActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentActivity = this

        googleMobileAdsConsentManager =
            GoogleMobileAdsConsentManager.getInstance(applicationContext)
        googleMobileAdsConsentManager.gatherConsent(this) { error ->
            if (googleMobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }
        }
        if (googleMobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }


        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.BLACK, Color.BLACK
            )
        )
        setContent {
            App()
        }
    }


    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }
        MobileAds.initialize(this) {}
    }

    override fun onResume() {
        super.onResume()
        loadInterstitialAd(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        minterstitialAd?.fullScreenContentCallback = null
        minterstitialAd = null
    }
}

fun loadInterstitialAd(context: Context) {
    val adInterstitialString = "ca-app-pub-3940256099942544/1033173712"
    InterstitialAd.load(
        context,
        adInterstitialString,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                minterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                minterstitialAd = interstitialAd
            }
        }
    )
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}