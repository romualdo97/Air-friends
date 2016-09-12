package com.worfu.helpers;

public interface AdActionResolver {
	/**
	 * Show or hide a banner ad
	 * @param show (boolean) Show or destroy a banner ad
	 * @see com.worfu.helpers.AdActionResolver#showAds(boolean)
	 */
	public void showAds(boolean show);
	
	/**
	 * Load or show a interstitial ad
	 * @see com.worfu.helpers.AdActionResolver#startInterstitial()
	 */
	public void startInterstitial();
	
	/**
	 * Load a interstitial ad but not show it
	 * @see com.worfu.helpers.AdActionResolver#loadInterstitialAd()
	 */
	public void loadInterstitialAd();
}
