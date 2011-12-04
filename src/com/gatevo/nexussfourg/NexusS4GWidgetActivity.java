package com.gatevo.nexussfourg;

import com.gatevo.nexussfourg.R;
import android.app.Activity;
import android.content.Context;
import android.net.*;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class NexusS4GWidgetActivity extends Activity {
	
    public void samsungWimax(boolean state) {
        //TextView txtObjectResult = (TextView) findViewById(R.id.objectResult);
        Context context = this;
        //http://forum.xda-developers.com/archive/index.php/t-909206.html
        Object samsungWimaxManager = context.getSystemService("WIMAX");
        //Log.d("samsungWimax",samsungWimaxManager.toString());
        if (samsungWimaxManager != null) {
            Method setWimaxEnabled = null;
            try {
                setWimaxEnabled = samsungWimaxManager.getClass().getMethod("setWimaxEnabled",
                        new Class[] { Boolean.TYPE });
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (state) {setWimaxEnabled.invoke(samsungWimaxManager, new Object[] { Boolean.TRUE });}
                else {setWimaxEnabled.invoke(samsungWimaxManager, new Object[] { Boolean.FALSE });}
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("samsungWimax","samsung WiMAX object toggle to " + state + " successful? Please wait 5-15 seconds for status bar icon to change...");
        }
        else {Log.d("samsungWimax","samsung WiMAX object is null");}
        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final ImageButton myButton = (ImageButton) findViewById(R.id_item.widgetButton);
        ConnectivityManager connection = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if(connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnected()){
        	myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_on));
        }
        
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	ConnectivityManager connection = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            	
            	Object wimaxManager = getApplicationContext().getSystemService("WiMax");
            	
            	
            	//Log.d("Class Name",wimaxManager.getClass().toString());
            	
            	Log.d("Type",String.valueOf(connection));
            	Log.d("Start Connecting?",Integer.toString(connection.startUsingNetworkFeature(ConnectivityManager.TYPE_WIMAX, "WiMax")));
            	//samsungWimax(true);
            	if(!connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnected()){
            		if(connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isAvailable()){
            			Log.d("samsungWimax","Trying on");
            			Log.d("Start Connecting?",Integer.toString(connection.startUsingNetworkFeature(ConnectivityManager.TYPE_WIMAX, "wimax")));
                		Log.d("Connection Info", connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).getDetailedState().toString());
                	}
            		if(connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnectedOrConnecting()){
            			myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_on));
            		}
            	}else {
            		if(connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isAvailable()){
            			Log.d("samsungWimax","Trying off");
            			//Log.d("Stop Connection?",Integer.toString(connection.stopUsingNetworkFeature(ConnectivityManager.TYPE_WIMAX, "WiMax_RC")));
                		Log.d("Connection Info", connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).getDetailedState().toString());
                	}
            		if(!connection.getNetworkInfo(ConnectivityManager.TYPE_WIMAX).isConnectedOrConnecting()){
            			myButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon));
            		}
            	}
            	//SystemProperties.set("wimax.wifi.disable", "1");

            }
        });

    }
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.i("ExampleWidget",  "Updating widgets " + Arrays.asList(appWidgetIds));
        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < N; i++) {
          int appWidgetId = appWidgetIds[i];
          // Create an Intent to launch ExampleActivity
          Intent intent = new Intent(context, NexusS4GWidgetActivity.class);
          PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
          // Get the layout for the App Widget and attach an on-click listener
          // to the button
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
          views.setOnClickPendingIntent(R.id_item.widgetButton, pendingIntent);
          // To update a label
          //views.setTextViewText(R.id.widget1label, df.format(new Date()));
          // Tell the AppWidgetManager to perform an update on the current app
          // widget
          appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}


/*public class ExampleAppWidgetProvider extends AppWidgetProvider {
  DateFormat df = new SimpleDateFormat("hh:mm:ss");
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    final int N = appWidgetIds.length;
    Log.i("ExampleWidget",  "Updating widgets " + Arrays.asList(appWidgetIds));
    // Perform this loop procedure for each App Widget that belongs to this
    // provider
    for (int i = 0; i < N; i++) {
      int appWidgetId = appWidgetIds[i];
      // Create an Intent to launch ExampleActivity
      Intent intent = new Intent(context, WidgetExampleActivity.class);
      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
      // Get the layout for the App Widget and attach an on-click listener
      // to the button
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget1);
      views.setOnClickPendingIntent(R.id.button, pendingIntent);
      // To update a label
      views.setTextViewText(R.id.widget1label, df.format(new Date()));
      // Tell the AppWidgetManager to perform an update on the current app
      // widget
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}*/
