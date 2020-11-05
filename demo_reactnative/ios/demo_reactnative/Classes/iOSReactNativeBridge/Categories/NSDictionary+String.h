//
//  NSDictionary+String.h
//  demo_reactnative
//
//  Created by Jason on 2020/9/18.
//

#import <Foundation/Foundation.h>

extern NSString *const atPlacementIdKey;
extern NSString *const atExtraKey;
extern NSString *const atErrMsgKey;

@interface NSDictionary (String)

- (NSString *)at_jsonString;

@end

