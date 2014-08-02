package ca.utoronto.ece1778.fragments;

import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.listener.OnGestureListener;
import ca.utoronto.ece1778.services.Result;
import ca.utoronto.ece1778.services.ResultService;
import ca.utoronto.ece1778.services.TempValue;

public class MeasurementFragment extends Fragment {

	FragmentHandler mmfh = null;
	SensorManager sm = null;
	TextView text_view_start = null;
	TextView text_view_result = null;
	TextView text_view_end = null;
	Button button = null;
	private static final int SHAKE_THRESHOLD = 400;
	private final int accurary = 1000;
	private final float FILTERING_VALAUE = 0.1f;
	private int final_flag = 0;
	private int staticMark = 0;
	//wait for 5 seconds
	private int staticMarkThreshold = 50;
	private long lastUpdate;
	private float last_lowX = 0f;
	private float last_lowY = 0f;
	private float last_lowZ = 0f;
	private float x = 0f;
	private float y = 0f;
	private float z = 0f;
	private float lowX = 0f;
	private float lowY = 0f;
	private float lowZ = 0f;
	private int x_angle_start = 0;
	private int y_angle_start = 0;
	private int z_angle_start = 0;
	private int x_angle_final = 0;
	private int y_angle_final = 0;
	private int z_angle_final = 0;
	private int final_angle = 0;
	private int x_axis = 0;
	private int y_axis = 0;
	private int z_axis = 0;
	private int total = 0;
	private String whichleg = "Left";
	Calendar dateAndTime = null;
	int year = 0;
	int month = 0;
	int day = 0;
	int hour = 0;
	int minute = 0;
	int second = 0;
	int timeint = 0;
	
	boolean isTTS = true;
	boolean isVibrate = true;
	boolean isButton  = false;
	boolean isGesture = false;
	boolean isClock   = true;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		mmfh = (FragmentHandler) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_measurement, container, false);

		return root;
	}

	@Override
	public void onStart() {
		
		super.onStart();
        	
		text_view_start  = (TextView) getActivity().findViewById(R.id.textview_start);
		text_view_end    = (TextView) getActivity().findViewById(R.id.textview_end);
		text_view_result = (TextView) getActivity().findViewById(R.id.textview_result);

		// Register the service for gravity sensor listener
		sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(gravity_listener,sm.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);

		this.getActivity();
		SharedPreferences preferences = this.getActivity().getSharedPreferences(MainActivity.appName, Context.MODE_PRIVATE);
		isTTS     = preferences.getBoolean(MainActivity.isTTS, isTTS);
		isVibrate = preferences.getBoolean(MainActivity.isVibrate, isVibrate);
		isButton  = preferences.getBoolean(MainActivity.isButton, isButton);
		isGesture = preferences.getBoolean(MainActivity.isGesture, isGesture);
		isClock   = preferences.getBoolean(MainActivity.isClock, isClock);
		
		
		
		if(isTTS){
			if (isClock)
				MainActivity.speak("Attach the phone to your ankle get into the start position and wait for instructions.");
	        else if (isButton)
	        	MainActivity.speak("Attach the phone to your ankle get into the start position and press the start button.");
	        else
	        	MainActivity.speak("Attach the phone to your ankle get into the start position and double click the screen to start.");
			
		}else{
			if (isClock)
	        	Toast.makeText(getActivity(), "Attach the phone to your ankle get into the start position and wait for instructions.", Toast.LENGTH_LONG).show();
	        else if (isButton)
	        	Toast.makeText(getActivity(), "Attach the phone to your ankle get into the start position and press the start button.", Toast.LENGTH_LONG).show();
	        else
	        	Toast.makeText(getActivity(), "Attach the phone to your ankle get into the start position and double click the screen to start.", Toast.LENGTH_LONG).show();
		}
			
		if(isVibrate)
			MainActivity.vibrate(200);
		
		if(isButton)
		{
			button = (Button) getActivity().findViewById(R.id.button_start_measurement);
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MeasurementFragment.this.nextStep();
				}
			});
		} 
		
		if(isGesture)
		{
			LinearLayout measureLayout = (LinearLayout)this.getActivity().findViewById(R.id.linear_layout_measurement);
			measureLayout.setOnTouchListener(new OnGestureListener(this.getActivity()){
				@Override
				public void oneFingerDoubleClick()
				{
					MeasurementFragment.this.nextStep();
				}
			});
		}
			
	}

	public void nextStep()
	{
		if (final_flag == 0) {

			final_flag = 1;

			if(isTTS){
				if (isClock)
					MainActivity.speak("Measurement started. Bend as much as you can and hold for 3 seconds.");
		        else if (isButton)
		        	MainActivity.speak("Measurement started. Bend as much as you can and press stop button.");
		        else
		        	MainActivity.speak("Measurement started. Bend as much as you can and double click to stop measurement.");
				
			}else{
				if (isClock)
		        	Toast.makeText(getActivity(), "Bend as much as you can and hold for 3 seconds.", Toast.LENGTH_LONG).show();
		        else if (isButton)
		        	Toast.makeText(getActivity(), "Bend as much as you can and press stop button.", Toast.LENGTH_LONG).show();
		        else
		        	Toast.makeText(getActivity(), "Bend as much as you can and double click to stop measurement.", Toast.LENGTH_LONG).show();
			}
			
			if(isVibrate)
				MainActivity.vibrate(new long[]{300, 200, 300}, false);
			
			if(isButton)
				button.setText("Stop");
			
		} else if (final_flag == 1) {

			final_flag = -1;
			dateAndTime = Calendar.getInstance(Locale.CANADA);
			TempValue.result = final_angle;
			
			if(isTTS)
				MainActivity.speak("Measurement complete.");
			
			if(isVibrate)
				MainActivity.vibrate(new long[]{200, 100, 200, 100, 200},  false);
			
			if(isButton)
				button.setText("Benchmark");

		} else if (final_flag == -1) {
			
			final_flag = 2;
			if(isClock && isTTS && !isButton && !isGesture)
				MainActivity.speak("Started at "+y_angle_start+" degree; Ended at "+y_angle_final + " degree; Range of Motion is " + final_angle + "degree.");
			if (isClock && !isTTS && !isButton && !isGesture)
				Toast.makeText(getActivity(), "Started at "+y_angle_start+"бу; Ended at "+y_angle_final + "бу; Range of Motion is " + final_angle + "бу.", Toast.LENGTH_LONG).show();
			
			year = dateAndTime.get(Calendar.YEAR);
			month = dateAndTime.get(Calendar.MONTH) + 1;
			day = dateAndTime.get(Calendar.DAY_OF_MONTH);
			hour = dateAndTime.get(Calendar.HOUR_OF_DAY);
			minute = dateAndTime.get(Calendar.MINUTE);
			second = dateAndTime.get(Calendar.SECOND);

			// Encode the current time into a unique number
			timeint = minute + hour * 60 + day * 60 * 24 + month * 60 * 24 * 31 + year * 60 * 24 * 31 * 12;
			
			//if users chose to measure right leg; the default value is to use left leg
			if (TempValue.leg == 2){
				whichleg = "Right";
			}			

			ResultService service = new ResultService(getActivity());
			Result result = new Result(final_angle, timeint, whichleg);

			//Save the data into database
			service.save(result);
			mmfh.buttonClicked(R.layout.fragment_view_benchmark);
		}
		
	}
	
	// Unregister the gravity sensor listener. It is necessary to release the resource.
	@Override
	public void onPause() {
		super.onPause();
	}

	// Define the behavior of the gravity sensor listener
	public SensorEventListener gravity_listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {

			long curTime = System.currentTimeMillis();
			// every 100 ms to test once
			if ((curTime - lastUpdate) > 100) {
				
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				// get the initial raw data
				x = event.values[0];
				y = event.values[1];
				z = event.values[2];

				// Low-Pass Filter in oder to filter out the very high frequency
				// value
				lowX = x * FILTERING_VALAUE + lowX * (1.0f - FILTERING_VALAUE);
				lowY = y * FILTERING_VALAUE + lowY * (1.0f - FILTERING_VALAUE);
				lowZ = z * FILTERING_VALAUE + lowZ * (1.0f - FILTERING_VALAUE);

				// define the speed calculation of sensor value changes
				float shakeSpeed = Math.abs(lowX + lowY + lowZ - last_lowX - last_lowY - last_lowZ) 	/ diffTime * 100000;

				if (final_flag == 0) {

					if(isClock && shakeSpeed < SHAKE_THRESHOLD)
					{
						staticMark += 1;
						System.out.println(shakeSpeed + " / " + SHAKE_THRESHOLD + "  mark : " + staticMark +"/"+staticMarkThreshold);
						if(staticMark >= staticMarkThreshold)
						{
							MeasurementFragment.this.nextStep();
							staticMark = 0;
						}
					}
					
					// Calculate the angles between x-axis and gravity; y-axis
					// and gravity; z-axis and gravity;
					x_axis = (int) (lowX * accurary);
					y_axis = (int) (lowY * accurary);
					z_axis = (int) (lowZ * accurary);
					total = (int) Math.sqrt(Math.pow(x_axis, 2) + Math.pow(y_axis, 2) + Math.pow(z_axis, 2));

					double x_total = x_axis / ((double) total);
					double y_total = y_axis / ((double) total);
					double z_total = z_axis / ((double) total);

					x_angle_start = (int) (Math.acos(x_total) * 180 / Math.PI);
					y_angle_start = (int) (Math.asin(y_total) * 180 / Math.PI);
					z_angle_start = (int) (Math.acos(z_total) * 180 / Math.PI);

					text_view_start.setText("Start at: " + y_angle_start + "бу");

				}

				if (final_flag == 1) {
					
					
					if(isClock && shakeSpeed < SHAKE_THRESHOLD)
					{
						staticMark += 1;
						if(staticMark >= staticMarkThreshold)
						{
							MeasurementFragment.this.nextStep();						
							staticMark = 0;
						}
					}

					// Calculate the angles between x-axis and gravity; y-axis
					// and gravity; z-axis and gravity;
					x_axis = (int) (lowX * accurary);
					y_axis = (int) (lowY * accurary);
					z_axis = (int) (lowZ * accurary);

					total = (int) Math.sqrt(Math.pow(x_axis, 2) + Math.pow(y_axis, 2) + Math.pow(z_axis, 2));

					double x_total = x_axis / ((double) total);
					double y_total = y_axis / ((double) total);
					double z_total = z_axis / ((double) total);

					x_angle_final = (int) (Math.acos(x_total) * 180 / Math.PI);
					y_angle_final = (int) (Math.asin(y_total) * 180 / Math.PI);
					z_angle_final = (int) (Math.acos(z_total) * 180 / Math.PI);

					if (Math.abs(x_angle_final - x_angle_start) + Math.abs(z_angle_final - z_angle_start) <= 90) {

						final_angle = Math.abs(y_angle_final - y_angle_start);

					} else {

						if (y_angle_final < 0)
							y_angle_final = -180 - y_angle_final;
					
						else
							y_angle_final = 180 - y_angle_final;

						final_angle = Math.abs(y_angle_final - y_angle_start);
					}

					text_view_end.setText("End at: " + y_angle_final + "бу");
					text_view_result.setText("Range of Motion: " + final_angle + "бу");

				}
				

				if (final_flag == -1) {
				
					if(isClock && !isButton && !isGesture && shakeSpeed < SHAKE_THRESHOLD)
					{
						staticMark += 1;
						if(staticMark >= staticMarkThreshold)
						{
							MeasurementFragment.this.nextStep();
							staticMark = 0;
						}
					}
				}

			}
			last_lowX = x;
			last_lowY = y;
			last_lowZ = z;
		}
	}; //this is end of gravity listener
}
