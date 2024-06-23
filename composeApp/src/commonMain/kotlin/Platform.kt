interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun showInterstitialAd(onAdDismissed: () -> Unit)