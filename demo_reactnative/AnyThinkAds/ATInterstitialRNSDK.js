import { NativeModules, NativeEventEmitter } from 'react-native';

const UseRewardedVideoAsInterstitial = 'UseRewardedVideoAsInterstitial';

const onInterstitialLoaded = 'ATInterstitialLoaded';
const onInterstitialFail = 'ATInterstitialLoadFail';
const onInterstitialAdShow = 'ATInterstitialAdShow';
const onInterstitialPlayStart = 'ATInterstitialPlayStart';
const onInterstitialPlayEnd = 'ATInterstitialPlayEnd';
const onInterstitialPlayFail = 'ATInterstitialPlayFail';
const onInterstitialClose = 'ATInterstitialClose';
const onInterstitialClick = 'ATInterstitialClick';

const ATInterstitialRNSDK = NativeModules.ATInterstitialRNSDK;
const ATInterstitialEventEmitter = new NativeEventEmitter(ATInterstitialRNSDK);


const setAdListener = (type, handler) => {
  switch (type) {
      case onInterstitialLoaded:
      case onInterstitialFail:
      case onInterstitialAdShow:
      case onInterstitialPlayStart:
      case onInterstitialPlayEnd:
      case onInterstitialPlayFail:
      case onInterstitialClose:
      case onInterstitialClick:
        removeAdListener(type);
        ATInterstitialEventEmitter.addListener(type, handler);
        break;
      default:
        console.log(`Event with type ${type} does not exist.`);
    }
};

const removeAdListener = (type) => {
 ATInterstitialEventEmitter.removeAllListeners(type);
};

const removeAllListeners = () => {
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialLoaded);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialFail);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialAdShow);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialPlayStart);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialPlayEnd);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialPlayFail);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialClose);
  ATInterstitialEventEmitter.removeAllListeners(onInterstitialClick);
}


module.exports = {
  ATInterstitialRNSDK,
  UseRewardedVideoAsInterstitial,

  onInterstitialLoaded,
  onInterstitialFail,
  onInterstitialAdShow,
  onInterstitialPlayStart,
  onInterstitialPlayEnd,
  onInterstitialPlayFail,
  onInterstitialClose,
  onInterstitialClick,

  setAdListener,
  removeAdListener,
  removeAllListeners,

  loadAd: (placementId, settings) =>  ATInterstitialRNSDK.loadAd(placementId, JSON.stringify(settings)),
  hasAdReady: (placementId) =>  ATInterstitialRNSDK.hasAdReady(placementId),
  checkAdStatus: (placementId) =>  ATInterstitialRNSDK.checkAdStatus(placementId),
  showAd: (placementId) =>  ATInterstitialRNSDK.showAd(placementId),
  showAdInScenario: (placementId, scenario) =>  ATInterstitialRNSDK.showAdInScenario(placementId, scenario),
};
