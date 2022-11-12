#ifndef _Included_uk_co_therhys_Request_Net_MacNet
#define _Included_uk_co_therhys_Request_Net_MacNet

#ifdef __cplusplus
extern "C" {
#endif


//
//  request
//  libreq
//
//  Created by Rhys Adams on 11/03/2022.
//  Copyright 2022. All rights reserved.
//

#import <Foundation/Foundation.h>
#include <jni.h>
#include "uk_co_therhys_Request_Net_MacNet.h"


const char* strtoa(JNIEnv* env, jstring data){
	return (*env)->GetStringUTFChars(env, data, NULL);
}

jstring atostr(JNIEnv* env, const char* data){
	return (*env)->NewStringUTF(env, data);
}

jbyteArray toByteArray(JNIEnv* env, const char* data, int length){
    jbyteArray out = (*env)->NewByteArray(env, length);

    (*env)->SetByteArrayRegion(env, out, 0, length, (jbyte*)data);

    return out;
}

const char* getStrArryItm(JNIEnv* env, jobjectArray arry, int x, int y){
	int len = (*env)->GetArrayLength(env, arry);
	
	return strtoa(env, (*env)->GetObjectArrayElement(env, (*env)->GetObjectArrayElement(env, arry, x), y));
}

/*
 * Class:     uk_co_therhys_Request_Net_MacNet
 * Method:    get
 * Signature: (Ljava/lang/String;[[Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jbyteArray JNICALL Java_uk_co_therhys_Request_Net_MacNet_get
  (JNIEnv* env, jobject this, jstring url, jobjectArray headers) {
 	 int num_headers = (*env)->GetArrayLength(env, headers);
 	 
 	NSMutableURLRequest* mutreq = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:[NSString stringWithUTF8String:strtoa(env, url)]]];

	unsigned int i;
	for (i=0; i < num_headers; i++) {
		const char* key = getStrArryItm(env, headers, i, 0);
		const char* val = getStrArryItm(env, headers, i, 1);
		
		[mutreq addValue:[NSString stringWithUTF8String:val] forHTTPHeaderField:[NSString stringWithUTF8String:key]];
	}
	
    NSURLRequest* req = [mutreq copy];

    NSURLResponse* res = nil;
	
	NSError* err = nil;
	
	NSData* resdata = [NSURLConnection sendSynchronousRequest:req returningResponse:&res error:&err];
	

	if(err == nil){
		const void* bytes = [resdata bytes];

        //[pool release];
		
		return toByteArray(env, bytes, [resdata length]);
	}
	
	NSLog(@"%@", [err localizedDescription]);

    //[pool release];
	
	return toByteArray(env, NULL, 0);
  }

/*
 * Class:     uk_co_therhys_Request_Net_MacNet
 * Method:    post_auth
 * Signature: (Ljava/lang/String;Ljava/lang/String;[[Ljava/lang/String;Ljava/lang/String;);
 */
JNIEXPORT jbyteArray JNICALL Java_uk_co_therhys_Request_Net_MacNet_post_1auth
  (JNIEnv* env, jobject this, jstring url, jstring data, jobjectArray headers) {
    //NSAutoreleasePool* pool = [[NSAutoreleasePool alloc] init];

 	 int num_headers = (*env)->GetArrayLength(env, headers);
 	 
 	NSMutableURLRequest* mutreq = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:[NSString stringWithUTF8String:strtoa(env, url)]]];

	unsigned int i;
	for (i=0; i < num_headers; i++) {
		const char* key = getStrArryItm(env, headers, i, 0);
		const char* val = getStrArryItm(env, headers, i, 1);
				
		[mutreq addValue:[NSString stringWithUTF8String:val] forHTTPHeaderField:[NSString stringWithUTF8String:key]];
	}
	
    NSString* postData = [NSString stringWithUTF8String:strtoa(env, data)];
    [mutreq setHTTPMethod: @"POST"];
    [mutreq setHTTPBody: [postData dataUsingEncoding:NSUTF8StringEncoding]];

    NSURLRequest* req = [mutreq copy];

    NSURLResponse* res = nil;

    NSError* err = nil;
	
    NSData* resdata = [NSURLConnection sendSynchronousRequest:req returningResponse:&res error:&err];
	
    char* out;
    if(err == nil || data == NULL){				
		int num_bytes = [resdata length];

		const void* bytes = [resdata bytes];

        //[pool release];

		return toByteArray(env, bytes, num_bytes);
	}
	
	NSLog(@"%@", [err localizedDescription]);

    //[pool release];
	
	return toByteArray(env, NULL, 0);
}

#ifdef __cplusplus
}
#endif
#endif
