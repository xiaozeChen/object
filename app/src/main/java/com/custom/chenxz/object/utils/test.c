//
// Created by Administrator on 2017/11/3.
//
//Bitmap.cpp
static jboolean Bitmap_compress(JNIEnv* env, jobject clazz, jlong bitmapHandle,
                                jint format, jint quality,
                                jobject jstream, jbyteArray jstorage) {

    LocalScopedBitmap bitmap(bitmapHandle);
    SkImageEncoder::Type fm;

    switch (format) {
    case kJPEG_JavaEncodeFormat:
        fm = SkImageEncoder::kJPEG_Type;
        break;
    case kPNG_JavaEncodeFormat:
        fm = SkImageEncoder::kPNG_Type;
        break;
    case kWEBP_JavaEncodeFormat:
        fm = SkImageEncoder::kWEBP_Type;
        break;
    default:
        return JNI_FALSE;
    }

    if (!bitmap.valid()) {
        return JNI_FALSE;
    }

    bool success = false;

    std::unique_ptr<SkWStream> strm(CreateJavaOutputStreamAdaptor(env, jstream, jstorage));
    if (!strm.get()) {
        return JNI_FALSE;
    }
    std::unique_ptr<SkImageEncoder> encoder(SkImageEncoder::Create(fm));
    if (encoder.get()) {
        SkBitmap skbitmap;
        bitmap->getSkBitmap(&skbitmap);
        success = encoder->encodeStream(strm.get(), skbitmap, quality);
    }
    return success ? JNI_TRUE : JNI_FALSE;
}
