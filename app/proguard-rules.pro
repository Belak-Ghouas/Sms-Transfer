# Keep source file names and line numbers for readable crash stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep generic type information needed by Gson/Retrofit for deserialization
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# ─── App data / domain models (Gson, Room, Retrofit) ────────────────────────
# Keep all classes in the models package so Gson can reflect over them
-keep class com.sms.pipe.data.models.** { *; }
-keep class com.sms.pipe.data.db.entities.** { *; }
-keep class com.sms.pipe.domain.** { *; }

# ─── Kotlin ──────────────────────────────────────────────────────────────────
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Lazy { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ─── Retrofit ────────────────────────────────────────────────────────────────
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# ─── OkHttp ──────────────────────────────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ─── Gson ────────────────────────────────────────────────────────────────────
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ─── Room ────────────────────────────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-dontwarn androidx.room.paging.**

# ─── Koin ────────────────────────────────────────────────────────────────────
-keep class org.koin.** { *; }
-keepnames class org.koin.**
-dontwarn org.koin.**

# ─── GreenRobot EventBus ─────────────────────────────────────────────────────
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# ─── Firebase / Crashlytics ──────────────────────────────────────────────────
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# ─── Slack API client ────────────────────────────────────────────────────────
-keep class com.slack.api.** { *; }
-dontwarn com.slack.api.**
# Slack SDK uses several JVM-only libraries internally
-dontwarn okhttp3.internal.platform.**
-dontwarn com.fasterxml.jackson.**
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }
-dontwarn ch.qos.logback.**

# ─── AndroidX Security (EncryptedSharedPreferences) ──────────────────────────
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

# ─── Google Play In-App Review ───────────────────────────────────────────────
-keep class com.google.android.play.core.review.** { *; }

# ─── WorkManager ─────────────────────────────────────────────────────────────
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}
-keep class androidx.work.** { *; }
-dontwarn androidx.work.**

# ─── Navigation component ────────────────────────────────────────────────────
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# ─── WebView (used for privacy policy display) ───────────────────────────────
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}

# ─── General Android / Java ──────────────────────────────────────────────────
-dontwarn java.awt.**
-dontwarn javax.swing.**
-dontwarn java.lang.invoke.**
-dontwarn sun.misc.**
