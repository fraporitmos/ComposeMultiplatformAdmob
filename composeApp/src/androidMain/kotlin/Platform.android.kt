import com.google.android.gms.ads.FullScreenContentCallback
import org.fraporitmos.englishtenses.MainActivity
import org.fraporitmos.englishtenses.MainActivity.Companion.getCurrentActivity

class AndroidPlatform : Platform {
    override val name: String = "Android"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun showInterstitialAd(onAdDismissed: () -> Unit) {
    if (MainActivity.minterstitialAd != null) {
        MainActivity.minterstitialAd!!.show(getCurrentActivity()!!)
        MainActivity.minterstitialAd!!.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    onAdDismissed()
                }
            }
    }
}
