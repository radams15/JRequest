ARCH ?= ppc
OSX_VER ?= 10.3

CFLAGS ?= -I /System/Library/Frameworks/JavaVM.framework/Headers/

../resources/librequest.dylib:
	gcc uk_co_therhys_Request_Net_MacNet.m -mmacosx-version-min=${OSX_VER} -arch ${ARCH} -fPIC -dynamiclib -o ../resources/librequest.dylib ${CFLAGS} -framework Foundation
