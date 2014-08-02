package ca.utoronto.ece1778;

import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import ca.utoronto.ece1778.fragments.FirstUserFragment;
import ca.utoronto.ece1778.fragments.InteractiveMethodFragment;
import ca.utoronto.ece1778.fragments.MeasureMethodFragment;
import ca.utoronto.ece1778.fragments.MeasurementFragment;
import ca.utoronto.ece1778.fragments.SecondUserFragment;
import ca.utoronto.ece1778.fragments.TutorialFragment;
import ca.utoronto.ece1778.fragments.UserProfileFragment;
import ca.utoronto.ece1778.fragments.ViewBenchmarkFragment;
import ca.utoronto.ece1778.fragments.ViewProgressFragment;
import ca.utoronto.ece1778.interfaces.FragmentHandler;

import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends FragmentActivity implements FragmentHandler, OnInitListener
{
	
	public static final String appName = "myKnee";
	public static final String preferencesCreated = "preferencesCreated";
	public static final String age = "age";
	public static final String sex = "sex";
	public static final String isSurgeried  = "isSurgeried";
	public static final String surgeryYear  = "surgeryYear";
	public static final String surgeryMonth = "surgeryMonth";
	public static final String surgeryDay   = "surgeryDay";
	public static final String isTTS        = "isTTS";
	public static final String isVibrate    = "isVibrate";
	public static final String isButton     = "isButton";
	public static final String isGesture    = "isGesture";
	public static final String isClock      = "isClock";
	public static final String interactiveM = "interactiveMethod";
	public static final String leg = "leg";
	public static final String first_install = "first_install";
	
	public final int DATA_CHECK_CODE = 0x1;
	public static TextToSpeech tts  = null;
	public static Vibrator vibrator = null;
	
	boolean isTTSOn     = true;
	boolean isVibrateOn = true;
	boolean isButtonOn  = false;
	boolean isGestureOn = false;
	boolean isClockOn   = true;
	
	int from_measurement_onback = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null)
		{
			from_measurement_onback = bundle.getInt("from_measurement_onback");
		}
		
		SharedPreferences preferences = this.getSharedPreferences(MainActivity.appName, MODE_PRIVATE);
		long preferencesCreated = preferences.getLong(MainActivity.preferencesCreated, 0);
		
		if (from_measurement_onback == 1){
			ViewBenchmarkFragment viewbenchmarkfragment = new ViewBenchmarkFragment();
			ft.add(R.id.frame_layout_main, viewbenchmarkfragment).commit();
			//Todo
			Intent checkTTSIntent = new Intent();
		    checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		    startActivityForResult(checkTTSIntent, DATA_CHECK_CODE);
		        
		    vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);		
			
		}else{
		
				if(preferencesCreated == 0)
				{
					FirstUserFragment firstUserFragment = new FirstUserFragment();
					ft.add(R.id.frame_layout_main, firstUserFragment).commit();
				}
				else
				{
					SecondUserFragment secondUserFragment = new SecondUserFragment();
					ft.add(R.id.frame_layout_main, secondUserFragment).commit();
				}
				
		        Intent checkTTSIntent = new Intent();
		        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		        startActivityForResult(checkTTSIntent, DATA_CHECK_CODE);
		        
		        vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
		
		}
	}

	
	@Override
	protected void onStart()
	{
		super.onRestart();
		EasyTracker.getInstance(this).activityStart(this);
		SharedPreferences preferences = this.getSharedPreferences(MainActivity.appName, Context.MODE_PRIVATE);
		isTTSOn     = preferences.getBoolean(MainActivity.isTTS, isTTSOn);
		isVibrateOn = preferences.getBoolean(MainActivity.isVibrate, isVibrateOn);
		isButtonOn  = preferences.getBoolean(MainActivity.isButton, isButtonOn);
		isGestureOn = preferences.getBoolean(MainActivity.isGesture, isGestureOn);
		isClockOn   = preferences.getBoolean(MainActivity.isClock, isClockOn);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == DATA_CHECK_CODE)
		{
			if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
				tts = new TextToSpeech(this, this);
			else
				Toast.makeText(this, "Voice data missing", Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if(tts != null)
			tts.shutdown();
		
		if(vibrator != null)
			vibrator = null;
	}


	@Override
	protected void onPause()
	{
		super.onPause();
		if(tts != null)
			tts.stop();
	}


	@Override
	public void onInit(int status)
	{
		if(status == TextToSpeech.SUCCESS)
		{
			int result = tts.setLanguage(Locale.US);
			if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
			{
				Toast.makeText(this, "Language not found or supported!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()){

    	case R.id.menu_tutorial:
    		buttonClicked(R.layout.fragment_tutorial);
    		break;
    	
    	case R.id.menu_profile:
    		buttonClicked(R.layout.fragment_user_profile);
			break;
			
    	case R.id.menu_progress:
    		buttonClicked(R.layout.fragment_view_my_progress);
    		break;
    		
		case R.id.menu_setting:
			buttonClicked(R.layout.fragment_interactive_method);
			break;
		
		case R.id.menu_contact:
			Intent data=new Intent(Intent.ACTION_SENDTO); 
			data.setData(Uri.parse("mailto:myknee@utoronto.ca")); 
			data.putExtra(Intent.EXTRA_SUBJECT, "Feedback for myKnee"); 
//			data.putExtra(Intent.EXTRA_TEXT, "Content"); 
			startActivity(data); 
			break;
			
		case R.id.menu_exit:
			System.exit(0);
			break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }

	@Override
	public void buttonClicked(int messageType) 
	{
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (messageType) {
		
		case R.layout.fragment_welcome_first_user:
			FirstUserFragment firstUserFragment = new FirstUserFragment();
			ft.replace(R.id.frame_layout_main, firstUserFragment);
			break;
			
		case R.layout.fragment_welcome_second_user:
			SecondUserFragment secondUserFragment = new SecondUserFragment();
			ft.replace(R.id.frame_layout_main, secondUserFragment);
			break;
			
		case R.layout.fragment_tutorial:
			TutorialFragment turialFrament = new TutorialFragment();
			ft.replace(R.id.frame_layout_main, turialFrament);
			break;
			
		case R.layout.fragment_user_profile:
			UserProfileFragment userProfileFrament = new UserProfileFragment();
			ft.replace(R.id.frame_layout_main, userProfileFrament);
			break;
			
		case R.layout.fragment_interactive_method:
			InteractiveMethodFragment InteractiveMethodFragment = new InteractiveMethodFragment();
			ft.replace(R.id.frame_layout_main, InteractiveMethodFragment);
			break;
		
		case R.layout.fragment_measure_method:
			MeasureMethodFragment measureMethodFragment = new MeasureMethodFragment();
			ft.replace(R.id.frame_layout_main, measureMethodFragment);
			break;
			
		case R.layout.fragment_measurement:
			MeasurementFragment measurementFragment = new MeasurementFragment();
			ft.replace(R.id.frame_layout_main, measurementFragment);
			break;
			
		case R.layout.fragment_view_benchmark:
			ViewBenchmarkFragment viewBenchmarkFragment = new ViewBenchmarkFragment();
			ft.replace(R.id.frame_layout_main, viewBenchmarkFragment);
			break;
			
		case R.layout.fragment_view_my_progress:
			ViewProgressFragment viewprogressFragment = new ViewProgressFragment();
			ft.replace(R.id.frame_layout_main, viewprogressFragment);
			
		default:
			break;
		}
		ft.commit();
	}
	
	public static void speak(String sentence)
	{
		if (tts != null)
			tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	public static void vibrate(long seconds)
	{
		vibrator.vibrate(seconds);
	}
	
	public static void vibrate(long[] pattern,boolean isRepeat)
	{
		vibrator.vibrate(pattern, isRepeat ? 1 : -1); 
	}


	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

}
