import { NativeModules, NativeEventEmitter } from 'react-native';

const PERSONALIZED = 0;
const NONPERSONALIZED = 1;
const UNKNOWN = 2;

const kATUserLocationInEU = 1;

//for android and ios
const OS_VERSION_NAME = 'os_vn';
const OS_VERSION_CODE = 'os_vc';
const APP_PACKAGE_NAME = 'package_name';
const APP_VERSION_NAME = 'app_vn';
const APP_VERSION_CODE = 'app_vc';

const BRAND = 'brand';
const MODEL = 'model';
const DEVICE_SCREEN_SIZE = 'screen';
const MNC = 'mnc';
const MCC = 'mcc';

const LANGUAGE = 'language';
const TIMEZONE = 'timezone';
const USER_AGENT = 'ua';
const ORIENTATION = 'orient';
const NETWORK_TYPE = 'network_type';

//for android
const INSTALLER = 'it_src';
const ANDROID_ID = 'android_id';
const GAID = 'gaid';
const MAC = 'mac';
const IMEI = 'imei';
const OAID = 'oaid';

//for ios
const IDFA = 'idfa';
const IDFV = 'idfv';

const ATRNSDK = NativeModules.ATRNSDK;
const ATRNSDKEventEmitter = new NativeEventEmitter(ATRNSDK);


module.exports = {
  ATRNSDK,
  PERSONALIZED,
  NONPERSONALIZED,
  UNKNOWN,
  kATUserLocationInEU,

  OS_VERSION_NAME,
  OS_VERSION_CODE,
  APP_PACKAGE_NAME,
  APP_VERSION_NAME,
  APP_VERSION_CODE,

  BRAND,
  MODEL,
  DEVICE_SCREEN_SIZE,
  MNC,
  MCC,

  LANGUAGE,
  TIMEZONE,
  USER_AGENT,
  ORIENTATION,
  NETWORK_TYPE,

  //for android
  INSTALLER,
  ANDROID_ID,
  GAID,
  MAC,
  IMEI,
  OAID,

  //for ios
  IDFA,
  IDFV,

  setLogDebug: (isDebug) => ATRNSDK.setLogDebug(isDebug),
  initSDK: (appId, appKey) => ATRNSDK.init(appId, appKey),
  initCustomMap: (customMap) => ATRNSDK.initCustomMap(JSON.stringify(customMap)),
  setPlacementCustomMap: (placementId, customMap) => ATRNSDK.setPlacementCustomMap(placementId, JSON.stringify(customMap)),
  setGDPRLevel: (level) => ATRNSDK.setGDPRLevel(level),
  getGDPRLevel: () => ATRNSDK.getGDPRLevel(),
  getUserLocation: () => ATRNSDK.getUserLocation(),
  showGDPRAuth: () => ATRNSDK.showGDPRAuth(),
  isChinaSDK: () => ATRNSDK.isChinaSDK(),
  setExcludeMyOfferPkgList: (excludeMyOfferArrays) => ATRNSDK.setExcludeMyOfferPkgList(excludeMyOfferArrays),
  getSDKVersionName: () => ATRNSDK.getSDKVersionName(),
  deniedUploadDeviceInfo: (deniedInfo) => ATRNSDK.deniedUploadDeviceInfo(deniedInfo),
};
