package com.dhs.kddevice.kddevice;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.dhs.kddevice.kddevice.util.SpeechUtil;

public class Instructs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructs);

        StringBuilder htmlFormattedText = new StringBuilder();

        htmlFormattedText.append("<h1>Welcome to your new eyes app <BR/>KD Device</h1><BR/>");

        htmlFormattedText.append("<p>Helping the Blind, this app will provide with features such as sending an SMS text of" );
        htmlFormattedText.append(" your current location and nearby locations such as hospitals, restaurant, and gas stations.");
        htmlFormattedText.append(" </p><p>To initially use the app, you will need to be assisted by a sighted person. Once you" );
        htmlFormattedText.append(" open the app our logo will appear, after that, a pop up will ask for accessing your ");
        htmlFormattedText.append("current location, only if you press yes will you be able to continue using the app. " );
        htmlFormattedText.append("</p><p>Another popup that will show up, is during the process of choosing your contacts." );
        htmlFormattedText.append(" </p><p>A popup will be shown asking if the app can retrieve contacts from your phone, and " );
        htmlFormattedText.append("select yes to continue. </p><p>Then you can have your assistant tap the contact button, which" );
        htmlFormattedText.append(" brings you to a screen with contacts listed, then you can choose certain people, to" );
        htmlFormattedText.append( " share your location with through a text. </p><p>Then hit the save button on the top to save " );
        htmlFormattedText.append("those contacts, and this step can be repeated multiple times, as long as you save the contacts." );
        htmlFormattedText.append(" </p><p>To find your current location you tap anywhere on the main button, and it will be read aloud to you." );
        htmlFormattedText.append(" </p><p>To send your current location to your saved contacts, tap the main button twice and " );
        htmlFormattedText.append("after the message is sent that will be read to you." );
        htmlFormattedText.append(" </p><p>To get your nearby locations, hit the long button on the top of the screen, which " );
        htmlFormattedText.append("moves to a new screen with 3 buttons. The top button is for nearby hospitals, the middle" );
        htmlFormattedText.append( " button is nearby gas stations and the bottom button is nearby restaurants. " );
        htmlFormattedText.append( "</p><p>Once you click on of those buttons, five locations will be given, then you can choose" );
        htmlFormattedText.append( " a location by clicking it then the route guidance will be spoken out to you. " );
        htmlFormattedText.append( "</p><p>To replay these guidelines, hit the instruction button on the button of the main screen," );
        htmlFormattedText.append( "as many times as needed.</p>");

        ((TextView)findViewById(R.id.textView2)).setText(Html.fromHtml(htmlFormattedText.toString()));


        SpeechUtil speechUtil = new SpeechUtil(getApplicationContext());
        String textView2 = "Welcome to your new eyes app KD Device:";

        speechUtil.speak(textView2);

        textView2 = " Helping the Blind, this app will provide with features such as sending an SMS text of" +
                " your current location and nearby locations such as hospitals, restaurant, and gas stations.";
        speechUtil.speak(textView2);

        textView2 =       " To initially use the app, you will need to be assisted by a sighted person. Once you" +
                " open the app our logo will appear, after that, a pop up will ask for accessing your " +
                "current location, only if you press yes will you be able to continue using the app. " +
                "Another popup that will show up, is during the process of choosing your contacts." +
                " A popup will be shown asking if the app can retrieve contacts from your phone, and " +
                "select yes to continue. Then you can have your assistant tap the contact button, which" +
                " brings you to a screen with contacts listed, then you can choose certain people, to" +
                " share your location with through a text. Then hit the save button on the top to save " +
                "those contacts, and this step can be repeated multiple times, as long as you save the contacts." +
                " To find your current location you tap anywhere on the main button, and it will be read aloud to you." +
                " To send your current location to your saved contacts, tap the main button twice and " +
                "after the message is sent that will be read to you." +
                " To get your nearby locations, hit the long button on the top of the screen, which " +
                "moves to a new screen with 3 buttons. The top button is for nearby hospitals, the middle" +
                " button is nearby gas stations and the bottom button is nearby restaurants. " +
                "Once you click on of those buttons, five locations will be given, then you can choose" +
                " a location by clicking it then the route guidance will be spoken out to you. " +
                "To replay these guidelines, hit the replay button on the button of the main screen," +
                "as many times as needed.";
        speechUtil.speak(textView2);
        // this code is for when the person clicks on the instruction button on the main activity xml

    }
}
