package ca.utoronto.ece1778.services;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibratorUtil {

	 public static void Vibrate(final Activity activity, long milliseconds) {   
         Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);   
         vib.vibrate(milliseconds);   
     }   
	 
     public static void Vibrate(final Activity activity, long[] pattern,boolean isRepeat) {   
         Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);   
         vib.vibrate(pattern, isRepeat ? 1 : -1);   
     }   
	
}
