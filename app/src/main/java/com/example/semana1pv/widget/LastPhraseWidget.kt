package com.example.semana1pv.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.semana1pv.MainActivity
import com.example.semana1pv.R
import com.example.semana1pv.data.local.AppDatabase
import kotlinx.coroutines.runBlocking

class LastPhraseWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { appWidgetId ->
            val last = runBlocking { AppDatabase.get(context).phraseDao().last() }

            val views = RemoteViews(context.packageName, R.layout.widget_last_phrase).apply {
                setTextViewText(
                    R.id.txtLastPhrase,
                    last?.text ?: "Sin frases aún"
                )
                val intent = Intent(context, MainActivity::class.java)
                val pending = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                setOnClickPendingIntent(R.id.widgetRoot, pending)
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
