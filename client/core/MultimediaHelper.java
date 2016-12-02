package com.kozhurov.project294.core;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import com.kozhurov.project294.R;

import java.util.List;
import java.util.Locale;

public final class MultimediaHelper {

    public static final int VOICE_REQUEST = 1;

    public void requestAudioCommand(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.say_your_command));


        try {
            activity.startActivityForResult(intent, VOICE_REQUEST);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activity, activity.getString(R.string.no_activity_for_this), Toast.LENGTH_SHORT).show();
        }
    }

    public void implementCommand(Resources resources, List<String> commandList, DeviceHelper deviceHelper){
        if(commandList == null || commandList.isEmpty()){
            return;
        }

        String targetCommand = commandList.get(0);

        Log.wtf("Multimedia manager", "target = "+targetCommand);

        if(resources.getString(R.string.command_turn_fan_on_main).contains(targetCommand)){
            deviceHelper.turnFan(true);
            return;
        }

        if(resources.getString(R.string.command_turn_fan_off_main).contains(targetCommand)){
            deviceHelper.turnFan(false);
            return;
        }
    }
}
