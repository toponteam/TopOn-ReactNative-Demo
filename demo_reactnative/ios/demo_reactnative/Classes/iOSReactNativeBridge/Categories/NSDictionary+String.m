//
//  NSDictionary+String.m
//  demo_reactnative
//
//  Created by Jason on 2020/9/18.
//

#import "NSDictionary+String.h"

NSString *const atPlacementIdKey = @"placementId";
NSString *const atExtraKey = @"adCallbackInfo";
NSString *const atErrMsgKey = @"errorMsg";

@implementation NSDictionary (String)

- (NSString *)at_jsonString {
  NSError *error;
  NSData *jsonData = [NSJSONSerialization dataWithJSONObject:self
                                                     options:kNilOptions
                                                       error:&error];
  
  if (!jsonData) {
      return @"{}";
  }
  
  return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

@end
