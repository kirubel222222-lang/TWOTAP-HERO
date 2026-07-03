package com.example.network

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsHelper(context: Context) {
    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logEvent(eventName: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    fun logContentSaved(url: String, type: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, url)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        }
        logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun logLearningSessionStarted(mode: String, category: String) {
        val bundle = Bundle().apply {
            putString("learning_mode", mode)
            putString("content_category", category)
        }
        logEvent("learning_session_started", bundle)
    }
    
    fun logAiUsage(feature: String) {
        val bundle = Bundle().apply {
            putString("ai_feature", feature)
        }
        logEvent("ai_usage", bundle)
    }
}
