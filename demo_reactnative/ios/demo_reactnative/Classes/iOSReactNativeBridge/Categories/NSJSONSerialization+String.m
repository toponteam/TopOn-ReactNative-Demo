//
//  NSJSONSerialization+String.m
//  demo_reactnative
//
//  Created by Jason on 2020/9/18.
//

#import "NSJSONSerialization+String.h"

@implementation NSJSONSerialization (String)

+ (id)at_JSONObjectWithString:(NSString *)string options:(NSJSONReadingOptions)opt error:(NSError *__autoreleasing *)error {
  return [self JSONObjectWithData:[string dataUsingEncoding:NSUTF8StringEncoding] options:opt error:error];
}

@end
