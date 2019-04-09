package com.bumble.headline.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.bumble.headline.R

class DialogBuilder {
    fun progressDialog(context: Context, title: String, content: String): AlertDialog.Builder {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setIcon(R.drawable.alert)
            .setMessage(content)
            .setNeutralButton(
                "Cancel"
            ) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setCancelable(true)
    }

    fun ExitDialog(activity: Activity, context: Context, title: String, content: String): AlertDialog.Builder {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setIcon(R.drawable.alert)
            .setMessage(content)
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                activity.finish()
            }
            .setNeutralButton(
                "Cancel"
            ) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setCancelable(true)
    }
}