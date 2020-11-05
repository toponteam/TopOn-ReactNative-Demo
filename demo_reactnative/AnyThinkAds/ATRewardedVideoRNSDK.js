import { NativeModules, NativeEventEmitter } from 'react-native';

const userIdKey = 'userIdKey';
const userDataKey = 'userDataKey';

const onRewardedVideoLoaded = 'ATRewardedVideoLoaded';
const onRewardedVideoFail = 'ATRewardedVideoLoadFail';
const onRewardedVideoPlayStart = 'ATRewardedVideoPlayStart';
const onRewardedVideoPlayEnd = 'ATRewardedVideoPlayEnd';
const onRewardedVideoPlayFail = 'ATRewardedVideoPlayFail';
const onRewardedVideoClose = 'ATRewardedVideoClose';
const onRewardedVideoClick = 'ATRewardedVideoClick';
const onRewardedVideoReward = 'ATRewardedVideoReward';


const ATRewardedVideoRNSDK = NativeModules.ATRewardedVideoRNSDK;
const ATRewardedVideoEventEmitter = new NativeEventEmitter(ATRewardedVideoRNSDK);


const setAdListener = (type, handler) => {
  switch (type) {
      case onRewardedVideoLoaded:
      case onRewardedVideoFail:
      case onRewardedVideoPlayStart:
      case onRewardedVideoPlayEnd:
      case onRewardedVideoPlayFail:
      case onRewardedVideoClose:
      case onRewardedVideoClick:
      case onRewardedVideoReward:
        removeAdListener(type);
        ATRewardedVideoEventEmitter.addListener(type, handler);
        break;
      default:
        console.log(`Event with type ${type} does not exist.`);
    }
};

const removeAdListener = (type) => {
 ATRewardedVideoEventEmitter.removeAllListeners(type);
};

const removeAllListeners = () => {
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoLoaded);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoFail);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoPlayStart);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoPlayEnd);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoPlayFail);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoClose);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoClick);
  ATRewardedVideoEventEmitter.removeAllListeners(onRewardedVideoReward);
}


module.exports = {
  ATRewardedVideoRNSDK,
  userIdKey,
  userDataKey,

  onRewardedVideoLoaded,
  onRewardedVideoFail,
  onRewardedVideoPlayStart,
  onRewardedVideoPlayEnd,
  onRewardedVideoPlayFail,
  onRewardedVideoClose,
  onRewardedVideoClick,
  onRewardedVideoReward,

  setAdListener,
  removeAdListener,
  removeAllListeners,

  loadAd: (placementId, settings) =>  ATRewardedVideoRNSDK.loadAd(placementId, JSON.stringify(settings)),
  hasAdReady: (placementId) =>  ATRewardedVideoRNSDK.hasAdReady(placementId),
  checkAdStatus: (placementId) =>  ATRewardedVideoRNSDK.checkAdStatus(placementId),
  showAd: (placementId) =>  ATRewardedVideoRNSDK.showAd(placementId),
  showAdInScenario: (placementId, scenario) =>  ATRewardedVideoRNSDK.showAdInScenario(placementId, scenario),
};
