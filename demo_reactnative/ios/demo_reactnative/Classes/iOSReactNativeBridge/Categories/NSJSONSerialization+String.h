//
//  NSJSONSerialization+String.h
//  demo_reactnative
//
//  Created by Jason on 2020/9/18.
//

#import <Foundation/Foundation.h>


NS_ASSUME_NONNULL_BEGIN


@interface NSJSONSerialization (String)

+ (id)at_JSONObjectWithString:(NSString *)string options:(NSJSONReadingOptions)opt error:(NSError *__autoreleasing *)error;

@end

NS_ASSUME_NONNULL_END
