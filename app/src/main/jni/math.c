//
// Created by Li on 2016/3/8.
//

#include "com_sola_sky_zyt_linktolove_myjni_Calculate.h"

JNIEXPORT jint JNICALL Java_com_sola_1sky_zyt_linktolove_myjni_Calculate_add
        (JNIEnv *env, jobject obj, jint param1, jint param2) {
    return param1 + param2;
}

