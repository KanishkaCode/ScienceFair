package com.dhs.kddevice.kddevice.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Chinnaraj on 1/3/2018.
 */

public class SpeechUtil {

    private TextToSpeech textToSpeech;

    public SpeechUtil(Context context) {
                textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
    }

    public void speak(String textToSpeak) {
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

}
