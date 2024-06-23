import SwiftUI
import GoogleMobileAds

@main
struct iOSApp: App {
    let ads = AdsViewModel()

    init(){
        GADMobileAds.sharedInstance().start(completionHandler: nil)
    }
    func showInterstitialAd() {
        let interstitial = InterstitialBanner.Interstitial()
        interstitial.showAd()
    }
	var body: some Scene {
        
		WindowGroup {
//            ZStack{
//                Button("click"){
//                    ads.showInterstitial = true
//                }
//            }
            
			ContentView().environmentObject(ads)
            BannerView()
                     .frame(width: 320, height: 50)
		}
	}
}
