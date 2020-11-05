import { NativeModules, NativeEventEmitter } from 'react-native';

const kATBannerAdLoadingExtraBannerAdSizeStruct = 'banner_ad_size_struct';
const kATBannerAdAdaptiveType = 'adaptive_type';
const kATBannerAdAdaptiveTypeAnchored = 0;
const kATBannerAdAdaptiveTypeInline = 1;
const kATBannerAdAdaptiveWidth = 'adaptive_width';
const kATBannerAdAdaptiveOrientation = 'adaptive_orientation';
const kATBannerAdAdaptiveOrientationCurrent = 0;
const kATBannerAdAdaptiveOrientationPortrait = 1;
const kATBannerAdAdaptiveOrientationLandscape = 2;

const kATBannerAdShowingPositionTop = 'top';
const kATBannerAdShowingPositionBottom = 'bottom';

const onBannerLoaded = 'ATBannerLoaded';
const onBannerFail = 'ATBannerLoadFail';
const onBannerCloseButtonTapped = 'ATBannerCloseButtonTapped';
const onBannerClick = 'ATBannerClick';
const onBannerShow = 'ATBannerShow';
const onBannerRefresh = 'ATBannerRefresh';
const onBannerRefreshFail = 'ATBannerRefreshFail';


const ATBannerRNSDK = NativeModules.ATBannerRNSDK;
const ATBannerEventEmitter = new NativeEventEmitter(ATBannerRNSDK);


const setAdListener = (type, handler) => {
  switch (type) {
      case onBannerLoaded:
      case onBannerFail:
      case onBannerCloseButtonTapped:
      case onBannerClick:
      case onBannerShow:
      case onBannerRefresh:
      case onBannerRefreshFail:
        removeAdListener(type);
        ATBannerEventEmitter.addListener(type, handler);
        break;
      default:
        console.log(`Event with type ${type} does not exist.`);
    }
};

const removeAdListener = (type) => {
 ATBannerEventEmitter.removeAllListeners(type);
};

const removeAllListeners = () => {
  ATBannerEventEmitter.removeAllListeners(onBannerLoaded);
  ATBannerEventEmitter.removeAllListeners(onBannerFail);
  ATBannerEventEmitter.removeAllListeners(onBannerCloseButtonTapped);
  ATBannerEventEmitter.removeAllListeners(onBannerClick);
  ATBannerEventEmitter.removeAllListeners(onBannerShow);
  ATBannerEventEmitter.removeAllListeners(onBannerRefresh);
  ATBannerEventEmitter.removeAllListeners(onBannerRefreshFail);
}

function createLoadAdSize(width, height) {
    var loadAdSize = {};
    loadAdSize["width"] = width;
    loadAdSize["height"] = height;
    return loadAdSize;
}

function createShowAdRect(x, y, width, height) {
    var adRect = {};
    adRect["x"] = x;
    adRect["y"] = y;
    adRect["width"] = width;
    adRect["height"] = height;
    return adRect;
}

function loadAd(placementId, settings) {
    if (settings.hasOwnProperty(kATBannerAdLoadingExtraBannerAdSizeStruct)) {
        var loadAdSize = settings[kATBannerAdLoadingExtraBannerAdSizeStruct];
        settings["width"] = loadAdSize["width"];
        settings["height"] = loadAdSize["height"];
        delete settings[kATBannerAdLoadingExtraBannerAdSizeStruct];
    }
    ATBannerRNSDK.loadAd(placementId, JSON.stringify(settings));
}


module.exports = {
  ATBannerRNSDK,
  kATBannerAdLoadingExtraBannerAdSizeStruct,
  kATBannerAdAdaptiveType,
  kATBannerAdAdaptiveTypeAnchored,
  kATBannerAdAdaptiveTypeInline,
  kATBannerAdAdaptiveWidth,
  kATBannerAdAdaptiveOrientation,
  kATBannerAdAdaptiveOrientationCurrent,
  kATBannerAdAdaptiveOrientationPortrait,
  kATBannerAdAdaptiveOrientationLandscape,

  kATBannerAdShowingPositionTop,
  kATBannerAdShowingPositionBottom,

  onBannerLoaded,
  onBannerFail,
  onBannerCloseButtonTapped,
  onBannerClick,
  onBannerShow,
  onBannerRefresh,
  onBannerRefreshFail,

  setAdListener,
  removeAdListener,
  removeAllListeners,

  createLoadAdSize,
  createShowAdRect,

  loadAd,
  hasAdReady: (placementId) =>  ATBannerRNSDK.hasAdReady(placementId),
  showAdInPosition: (placementId, position) =>  ATBannerRNSDK.showAdInPosition(placementId, position),
  showAdInRectangle: (placementId, showAdRect) =>  ATBannerRNSDK.showAdInRectangle(placementId, JSON.stringify(showAdRect)),
  hideAd: (placementId) =>  ATBannerRNSDK.hideAd(placementId),
  reShowAd: (placementId) =>  ATBannerRNSDK.reShowAd(placementId),
  removeAd: (placementId) =>  ATBannerRNSDK.removeAd(placementId),
};
