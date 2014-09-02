package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.services.ResultService;

public class InteractiveMethodFragment extends Fragment
{

	FragmentHandler fh = null;
	SharedPreferences preferences = null;
	private ResultService resultservice = null;
	Editor editor = null;

	boolean isTTS = true;
	Button ttsOn = null;
	Button ttsOff = null;

	boolean isVibrate = true;
	Button vibrateOn = null;
	Button vibrateOff = null;

	boolean isButton = false;
	Button buttonOn = null;
	Button buttonOff = null;

	boolean isGesture = false;
	Button gestureOn = null;
	Button gestureOff = null;

	boolean isClock = true;
	Button clockOn = null;
	Button clockOff = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_interactive_method, container, false);

		// get database service
		resultservice = new ResultService(getActivity());
		
		preferences = this.getActivity().getSharedPreferences(MainActivity.appName, Activity.MODE_PRIVATE);
		editor = preferences.edit();

		ttsOn = (Button) root.findViewById(R.id.button_tts_on);
		ttsOff = (Button) root.findViewById(R.id.button_tts_off);

		isTTS = preferences.getBoolean(MainActivity.isTTS, isTTS);
		if (isTTS)
		{
			ttsOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			ttsOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		} else
		{
			ttsOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			ttsOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}

		ttsOn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if(TextToSpeech.Engine.CHECK_VOICE_DATA_PASS == 1){
					isTTS = true;
					ttsOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
					ttsOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				}else
					Toast.makeText(getActivity(), "Voice data missing", Toast.LENGTH_SHORT).show();
				
			}
		});

		ttsOff.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isTTS = false;
				ttsOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				ttsOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		vibrateOn = (Button) root.findViewById(R.id.button_vibrate_on);
		vibrateOff = (Button) root.findViewById(R.id.button_vibrate_off);

		isVibrate = preferences.getBoolean(MainActivity.isVibrate, isVibrate);
		if (isVibrate)
		{
			vibrateOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			vibrateOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		} else
		{
			vibrateOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			vibrateOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}

		vibrateOn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isVibrate = true;
				vibrateOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				vibrateOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		vibrateOff.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isVibrate = false;
				vibrateOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				vibrateOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		buttonOn = (Button) root.findViewById(R.id.button_button_on);
		buttonOff = (Button) root.findViewById(R.id.button_button_off);

		isButton = preferences.getBoolean(MainActivity.isButton, isButton);
		if (isButton)
		{
			buttonOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		} else
		{
			buttonOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}

		buttonOn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isButton = true;
				buttonOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		buttonOff.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isButton = false;
				buttonOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		gestureOn = (Button) root.findViewById(R.id.button_gesture_on);
		gestureOff = (Button) root.findViewById(R.id.button_gesture_off);

		isGesture = preferences.getBoolean(MainActivity.isGesture, isGesture);
		if (isGesture)
		{
			gestureOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			gestureOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		} else
		{
			gestureOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			gestureOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}

		gestureOn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isGesture = true;
				gestureOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				gestureOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		gestureOff.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isGesture = false;
				gestureOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				gestureOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		clockOn = (Button) root.findViewById(R.id.button_clock_on);
		clockOff = (Button) root.findViewById(R.id.button_clock_off);

		isClock = preferences.getBoolean(MainActivity.isClock, isClock);
		if (isClock)
		{
			clockOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			clockOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		} else
		{
			clockOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			clockOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}

		clockOn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isClock = true;
				clockOn.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				clockOff.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		clockOff.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				isClock = false;
				clockOff.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				clockOn.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});

		Button deleteProfile = (Button) root.findViewById(R.id.button_delete_profile);
		deleteProfile.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
				adb.setTitle("Delete your profile?");
				adb.setMessage("Do you want to delete your profile ");
				// final int user_id = Integer.parseInt(v.getTag().toString());
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("OK", new AlertDialog.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{	
						int flag_first_install = preferences.getInt(MainActivity.first_install, 0);
						preferences.edit().clear().commit();
						editor.putInt(MainActivity.first_install, flag_first_install).commit();
					}
				});
				adb.show();


			}
		});

		Button deleteProgress = (Button) root.findViewById(R.id.button_delete_progress);
		deleteProgress.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
				adb.setTitle("Delete your progress?");
				adb.setMessage("Do you want to delete all data");
				// final int user_id = Integer.parseInt(v.getTag().toString());
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("OK", new AlertDialog.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{					
						resultservice.deleteall();
					}
				});
				adb.show();
			}
		});

		Button settingDone = (Button) root.findViewById(R.id.button_interactive_done);
		settingDone.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				long preferencesCreated = preferences.getLong(MainActivity.preferencesCreated, 0);

				editor.putBoolean(MainActivity.isTTS, isTTS).putBoolean(MainActivity.isVibrate,isVibrate)
				.putBoolean(MainActivity.isButton, isButton).putBoolean(MainActivity.isGesture, isGesture).putBoolean(MainActivity.isClock, isClock)
				.commit();
				
				if (preferencesCreated == 0)
				{
					fh.buttonClicked(R.layout.fragment_welcome_first_user);
				} else
				{
					fh.buttonClicked(R.layout.fragment_welcome_second_user);
				}

			}
		});

		return root;
	}

	@Override
	public void onAttach(Activity activity)
	{

		super.onAttach(activity);

		fh = (FragmentHandler) activity;
	}

}
