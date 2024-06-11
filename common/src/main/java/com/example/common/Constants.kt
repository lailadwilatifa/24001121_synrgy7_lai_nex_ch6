package com.example.common

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val key = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYTMxYTk4MmVlMDJiNzM4OWVjMGI0ZDgxMmQ1YmIwZiIsInN1YiI6IjY2NDU2Yzg0ODJlMGM3NzIyNjJlZWRmOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VL6oyYdkRMdNxenL2-CAbWnhi5NTYibrGNLiXrjV9lQ"

    @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    @JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1


    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"
    //workers
    const val OUTPUT_PATH = "blur_filter_outputs"
    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
    const val TAG_OUTPUT = "OUTPUT"

    const val DELAY_TIME_MILLIS: Long = 3000
}