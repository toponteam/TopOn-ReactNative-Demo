import React, {Component} from 'react';
import { Platform, Dimensions } from 'react-native';

import {StyleSheet, Text, View, ScrollView, TouchableOpacity} from 'react-native';

import {
    ATRNSDK,
    ATRewardedVideoRNSDK,
    ATInterstitialRNSDK,
    ATBannerRNSDK
} from './AnyThinkAds/ATReactNativeSDK';

export default class App extends Component {

    componentDidMount() {
     console.log('componentDidMount ....');

     if (Platform.OS === 'android') {
             this.appId = 'a5aa1f9deda26d';
             this.appKey = '4f7b9ac17decb9babec83aac078742c7';
             this.rewardedVideoPlacementId = 'b5b449fb3d89d7';
             this.interstitialPlacementId = 'b5baca53984692';
             this.bannerPlacementId = 'b5baca4f74c3d8';

             this.rewardedVideoScenarioId = 'f5e54970dc84e6';
             this.interstitialScenarioId = 'f5e549727efc49';

         } else if (Platform.OS === 'ios') {
             this.appId = 'a5b0e8491845b3';
             this.appKey = '7eae0567827cfe2b22874061763f30c9';
             this.rewardedVideoPlacementId = 'b5b44a0f115321';
             this.interstitialPlacementId = 'b5bacad26a752a';
             this.bannerPlacementId = 'b5bacaccb61c29';
             this.rewardedVideoScenarioId = 'f5e54970dc84e6';
             this.interstitialScenarioId = 'f5e549727efc49';

         }

         this.deviceWidthInPixel = Dimensions.get('window').width * Dimensions.get('window').scale;

         this.initTopOnSDK();
    }

   initTopOnSDK = () => {
       ATRNSDK.setLogDebug(true);

       ATRNSDK.getSDKVersionName().then(versionName => {
           console.log('TopOn SDK version name: ' + versionName);
       });

       if (Platform.OS === 'android') {
           ATRNSDK.isChinaSDK().then(isChinaSDK => {
                console.log('isChinaSDK: ' + isChinaSDK);
           });
       }

       ATRNSDK.setExcludeMyOfferPkgList(
           [
               "com.exclude.myoffer1",
               "com.exclude.myoffer2",
           ]
       );

       var customMap = {
           "appCustomKey1": "appCustomValue1",
           "appCustomKey2" : "appCustomValue2"
       };
       ATRNSDK.initCustomMap(customMap);

       var placementCustomMap = {
           "placementCustomKey1": "placementCustomValue1",
           "placementCustomKey2" : "placementCustomValue2"
       };
       ATRNSDK.setPlacementCustomMap(this.rewardedVideoPlacementId, placementCustomMap);

       ATRNSDK.setGDPRLevel(ATRNSDK.PERSONALIZED);

       ATRNSDK.getUserLocation().then(userLocation => {
            console.log('userLocation: ' + userLocation);
            if (userLocation == ATRNSDK.kATUserLocationInEU) {
                console.log('userLocation: in EU');
                ATRNSDK.getGDPRLevel().then((level) => {
                   console.log('gdpr level: ' + level);
                   if (level == ATRNSDK.UNKNOWN) {
                        ATRNSDK.showGDPRAuth();
                   }
                })
            } else {
                console.log('userLocation: not in EU');
            }
       })

       console.log('TopOn SDK init ....')
       ATRNSDK.initSDK(this.appId, this.appKey);

       this.initAdListener();
   }

   initAdListener = () => {
       this.initRewardedVideoAdListener();
       this.initInterstitialAdListener();
       this.initBannerAdListener();
   }

   initRewardedVideoAdListener = () => {
       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoLoaded, (event) => {
         console.log('ATRewardedVideoLoaded: ' + event.placementId);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoFail, (event) => {
         console.warn('ATRewardedVideoLoadFail: ' + event.placementId + ', errorMsg: ' + event.errorMsg);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoPlayStart, (event) => {
         console.log('ATRewardedVideoPlayStart: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoPlayEnd, (event) => {
         console.log('ATRewardedVideoPlayEnd: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoPlayFail, (event) => {
         console.log('ATRewardedVideoPlayFail: ' + event.placementId + ', errorMsg: ' + event.errorMsg + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoClick, (event) => {
         console.log('ATRewardedVideoClick: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoReward, (event) => {
         console.log('ATRewardedVideoReward: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATRewardedVideoRNSDK.setAdListener(ATRewardedVideoRNSDK.onRewardedVideoClose, (event) => {
         console.log('ATRewardedVideoClose: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });
   }

   initInterstitialAdListener = () => {
       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialLoaded, (event) => {
         console.log('ATInterstitialLoaded: ' + event.placementId);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialFail, (event) => {
         console.warn('ATInterstitialLoadFail: ' + event.placementId + ', errorMsg: ' + event.errorMsg);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialAdShow, (event) => {
         console.log('ATInterstitialAdShow: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialPlayStart, (event) => {
         console.log('ATInterstitialPlayStart: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialPlayEnd, (event) => {
         console.log('ATInterstitialPlayEnd: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialPlayFail, (event) => {
         console.log('ATInterstitialPlayFail: ' + event.placementId + ', errorMsg: ' + event.errorMsg + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialClick, (event) => {
         console.log('ATInterstitialClick: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATInterstitialRNSDK.setAdListener(ATInterstitialRNSDK.onInterstitialClose, (event) => {
         console.log('ATInterstitialClose: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });
   }

   initBannerAdListener = () => {
       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerLoaded, (event) => {
         console.log('ATBannerLoaded: ' + event.placementId);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerFail, (event) => {
         console.warn('ATBannerLoadFail: ' + event.placementId + ', errorMsg: ' + event.errorMsg);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerShow, (event) => {
         console.log('ATBannerShow: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerCloseButtonTapped, (event) => {
         console.log('ATBannerCloseButtonTapped: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerClick, (event) => {
         console.log('ATBannerClick: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerRefresh, (event) => {
         console.log('ATBannerRefresh: ' + event.placementId + ', errorMsg: ' + event.errorMsg + ', adCallbackInfo: ' + event.adCallbackInfo);
       });

       ATBannerRNSDK.setAdListener(ATBannerRNSDK.onBannerRefreshFail, (event) => {
         console.log('ATBannerRefreshFail: ' + event.placementId + ', adCallbackInfo: ' + event.adCallbackInfo);
       });
   }

  // RewardedVideo --------------------------------------------------------------------------------------------------------------------------
  loadRewardedVideo = () => {
    console.log('loadRewardedVideo ....');

    var settings = {};
    settings[ATRewardedVideoRNSDK.userIdKey] = "test_user_id";
    settings[ATRewardedVideoRNSDK.userDataKey] = "test_user_data";

    ATRewardedVideoRNSDK.loadAd(this.rewardedVideoPlacementId, settings);
  };

  isRewardedVideoReady = () => {
   ATRewardedVideoRNSDK.hasAdReady(this.rewardedVideoPlacementId).then(isAdReady => {
    console.log("isRewardedVideoReady: hasAdReady: " + isAdReady)
   });

   ATRewardedVideoRNSDK.checkAdStatus(this.rewardedVideoPlacementId).then(adStatusInfo => {
    console.log("isRewardedVideoReady: checkAdStatus: " + adStatusInfo)
   });
  };

  showRewardedVideo = () => {
    console.log('showRewardedVideo ....');
    // ATRewardedVideoRNSDK.showAd(this.rewardedVideoPlacementId);
   ATRewardedVideoRNSDK.showAdInScenario(this.rewardedVideoPlacementId, this.rewardedVideoScenarioId);
  };

  // Interstitial --------------------------------------------------------------------------------------------------------------------------
  loadInterstitial = () => {
    console.log('loadInterstitial ....');

    var settings = {};
    settings[ATInterstitialRNSDK.UseRewardedVideoAsInterstitial] = false;
//    settings[ATInterstitialRNSDK.UseRewardedVideoAsInterstitial] = true;

    ATInterstitialRNSDK.loadAd(this.interstitialPlacementId, settings);
  };

  isInterstitialReady = () => {
   ATInterstitialRNSDK.hasAdReady(this.interstitialPlacementId).then(isAdReady => {
    console.log("isInterstitialReady: " + isAdReady)
   });

   ATInterstitialRNSDK.checkAdStatus(this.interstitialPlacementId).then(adStatusInfo => {
    console.log("isInterstitialReady: checkAdStatus: " + adStatusInfo)
   });
  };

  showInterstitial = () => {
    console.log('showInterstitial ....');
    // ATInterstitialRNSDK.showAd(this.interstitialPlacementId);
   ATInterstitialRNSDK.showAdInScenario(this.interstitialPlacementId, this.interstitialScenarioId);
  };

  // Banner --------------------------------------------------------------------------------------------------------------------------
  loadBanner = () => {
    console.log('loadBanner ....');

    var settings = {};
    if (Platform.OS === 'android') {
      settings[ATBannerRNSDK.kATBannerAdLoadingExtraBannerAdSizeStruct] = ATBannerRNSDK.createLoadAdSize(this.deviceWidthInPixel, this.deviceWidthInPixel * 50/320);
      
      settings[ATBannerRNSDK.kATBannerAdAdaptiveWidth] = this.deviceWidthInPixel;
      settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationCurrent;
//    settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationPortrait;
//    settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationLandscape;
    } 
    else if (Platform.OS === 'ios') {
      settings[ATBannerRNSDK.kATBannerAdLoadingExtraBannerAdSizeStruct] = ATBannerRNSDK.createLoadAdSize(320, 50);
      
      settings[ATBannerRNSDK.kATBannerAdAdaptiveWidth] = 320;
      settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationCurrent;
//    settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationPortrait;
//    settings[ATBannerRNSDK.kATBannerAdAdaptiveOrientation] = ATBannerRNSDK.kATBannerAdAdaptiveOrientationLandscape;
    }

    ATBannerRNSDK.loadAd(this.bannerPlacementId, settings);
  };

  isBannerReady = () => {
   ATBannerRNSDK.hasAdReady(this.bannerPlacementId).then(isAdReady => {
    console.log("isBannerReady: " + isAdReady)
   });
  };

  showBanner = () => {
    console.log('showBanner ....');
//    ATBannerRNSDK.showAdInPosition(this.bannerPlacementId, ATBannerRNSDK.kATBannerAdShowingPositionTop);
    ATBannerRNSDK.showAdInPosition(this.bannerPlacementId, ATBannerRNSDK.kATBannerAdShowingPositionBottom);
//    ATBannerRNSDK.showAdInRectangle(this.bannerPlacementId,  ATBannerRNSDK.createShowAdRect(0, 0, this.deviceWidthInPixel, this.deviceWidthInPixel * 50/320));
  };

  hideBanner = () => {
    console.log('hideBanner ....');
    ATBannerRNSDK.hideAd(this.bannerPlacementId);
  }

  reShowBanner = () => {
    console.log('reShowBanner ....');
    ATBannerRNSDK.reShowAd(this.bannerPlacementId);
  }

  removeBanner = () => {
    console.log('removeBanner ....');
    ATBannerRNSDK.removeAd(this.bannerPlacementId);
  }

  componentWillUnmount() {
    console.log('componentWillUnmount ....');

    ATRewardedVideoRNSDK.removeAllListeners();
    ATInterstitialRNSDK.removeAllListeners();
    ATBannerRNSDK.removeAllListeners();
  }


  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.title}>TopOn SDK Demo</Text>
        <View style={styles.buttonContainer}>
          <TouchableOpacity onPress={this.loadRewardedVideo}>
            <Text style={styles.button}>Load Rewarded Video</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.isRewardedVideoReady}>
            <Text style={styles.button}>isRewardedVideoReady?</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.showRewardedVideo}>
            <Text style={styles.button}>Show Rewarded Video</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity onPress={this.loadInterstitial}>
            <Text style={styles.button}>Load Interstitial</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.isInterstitialReady}>
            <Text style={styles.button}>isInterstitialReady?</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.showInterstitial}>
            <Text style={styles.button}>Show Interstitial</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity onPress={this.loadBanner}>
            <Text style={styles.button}>Load Banner</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.isBannerReady}>
            <Text style={styles.button}>isBannerReady?</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.showBanner}>
            <Text style={styles.button}>Show Banner</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.hideBanner}>
            <Text style={styles.button}>Hide Banner</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.reShowBanner}>
            <Text style={styles.button}>ReShow Banner</Text>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.removeBanner}>
            <Text style={styles.button}>Remove Banner</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
  },
  buttonContainer: {
    margin: 10
  },
  title: {
     fontSize: 30,
     marginTop: 30,
     marginBottom: 10,
   },
  button: {
    alignSelf: 'center',
    fontSize: 21,
    borderRadius: 3,
    backgroundColor: '#607f7f7f',
    padding: 2,
    margin: 5,
  },
});
