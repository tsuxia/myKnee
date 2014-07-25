package ca.utoronto.ece1778.fragments;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;

public class UserProfileFragment extends Fragment {

	public static int MALE = 1;
	public static int FEMAIL = 2;
	
	public static int LEFT = 0;
	public static int BOTH = 1;
	public static int RIGHT = 2;
	
	public static int COLOR_SETTED = Color.parseColor("#40a9f5");
	public static int COLOR_UNSET  = Color.parseColor("#cdcdcd");
	
	
	int age = 30;
	int sex = MALE;
	boolean isSurgeried = true;
	int surgeryYear = 2011;
	int surgeryMonth = 8;
	int surgeryDay = 15;
	int leg = LEFT;
	
	FragmentHandler upf = null;
	TextView textViewAge = null;
	Button buttonMale = null;
	Button buttonFemale = null;
	Button buttonIsSurgery = null;
	Button buttonNoSurgery = null;
	
	TextView textViewTimeQuestion = null;
	TextView textViewTimeAnswer = null;
	TextView textViewKneeQuestion = null;
	LinearLayout linearLayoutKnees = null;
	
	Button buttonLeftLeg = null;
	Button buttonBothLegs = null;
	Button buttonRightLeg = null;
	
	SharedPreferences preferences = null;
	Editor editor = null;

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);
		upf = (FragmentHandler) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View root = inflater.inflate(R.layout.fragment_user_profile, container,	false);
		
		preferences = this.getActivity().getSharedPreferences(MainActivity.appName, Activity.MODE_PRIVATE);
		editor = preferences.edit();
		age = preferences.getInt("age", age);
		textViewAge = (TextView)root.findViewById(R.id.text_view_age);
		textViewAge.setText(""+age);
		
		Button buttonIncreaseAge = (Button)root.findViewById(R.id.button_increase_age);
		buttonIncreaseAge.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				age = age + 1;
				textViewAge.setText(""+age);
			}
		});
		
		Button buttonDecreaseAge = (Button)root.findViewById(R.id.button_decrease_age);
		buttonDecreaseAge.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				age = age - 1;
				textViewAge.setText(""+age);
			}
		});
		
		buttonMale = (Button)root.findViewById(R.id.button_male);
		buttonFemale = (Button)root.findViewById(R.id.button_female);

		sex = preferences.getInt(MainActivity.sex, UserProfileFragment.MALE);
		if(sex == UserProfileFragment.MALE)
		{
			buttonMale.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonFemale.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}
		else
		{
			buttonFemale.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonMale.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}
		
		buttonMale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				sex = UserProfileFragment.MALE;
				buttonMale.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonFemale.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		buttonFemale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				sex = UserProfileFragment.FEMAIL;
				buttonFemale.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonMale.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		textViewTimeQuestion = (TextView)root.findViewById(R.id.text_view_surgery_time_question);
		textViewTimeAnswer = (TextView)root.findViewById(R.id.text_view_surgery_time);
		textViewKneeQuestion = (TextView)root.findViewById(R.id.text_view_surgery_knee_question);
		linearLayoutKnees = (LinearLayout)root.findViewById(R.id.linear_layout_knees);
		buttonIsSurgery = (Button)root.findViewById(R.id.button_is_surgery);
		buttonNoSurgery = (Button)root.findViewById(R.id.button_no_surgery);
		
		isSurgeried = preferences.getBoolean(MainActivity.isSurgeried, true);
		if(isSurgeried)
		{
			buttonIsSurgery.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonNoSurgery.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			textViewTimeQuestion.setVisibility(View.VISIBLE);
			textViewTimeAnswer.setVisibility(View.VISIBLE);
			textViewKneeQuestion.setVisibility(View.VISIBLE);
			linearLayoutKnees.setVisibility(View.VISIBLE);
		}
		else
		{
			buttonNoSurgery.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonIsSurgery.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			textViewTimeQuestion.setVisibility(View.INVISIBLE);
			textViewTimeAnswer.setVisibility(View.INVISIBLE);
			textViewKneeQuestion.setVisibility(View.INVISIBLE);
			linearLayoutKnees.setVisibility(View.INVISIBLE);
		}
		
		buttonIsSurgery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				isSurgeried = true;
				buttonIsSurgery.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonNoSurgery.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				textViewTimeQuestion.setVisibility(View.VISIBLE);
				textViewTimeAnswer.setVisibility(View.VISIBLE);
				textViewKneeQuestion.setVisibility(View.VISIBLE);
				linearLayoutKnees.setVisibility(View.VISIBLE);
			}
		});
		
		buttonNoSurgery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				isSurgeried = false;
				buttonNoSurgery.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonIsSurgery.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				textViewTimeQuestion.setVisibility(View.INVISIBLE);
				textViewTimeAnswer.setVisibility(View.INVISIBLE);
				textViewKneeQuestion.setVisibility(View.INVISIBLE);
				linearLayoutKnees.setVisibility(View.INVISIBLE);
			}
		});
		
		buttonLeftLeg = (Button)root.findViewById(R.id.button_surgery_left);
		buttonBothLegs = (Button)root.findViewById(R.id.button_surgery_both);
		buttonRightLeg = (Button)root.findViewById(R.id.button_surgery_right);

		leg = preferences.getInt(MainActivity.leg, UserProfileFragment.LEFT);
		if(leg == UserProfileFragment.LEFT)
		{
			buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}
		else if(leg == UserProfileFragment.BOTH)
		{
			buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
		}
		else if(leg == UserProfileFragment.RIGHT)
		{
			buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
		}
		
		buttonLeftLeg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				leg = UserProfileFragment.LEFT;
				buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		buttonBothLegs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				leg = UserProfileFragment.BOTH;
				buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		buttonRightLeg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				leg = UserProfileFragment.RIGHT;
				buttonLeftLeg.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				buttonBothLegs.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
				buttonRightLeg.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
			}
		});
		
		
		surgeryYear = preferences.getInt(MainActivity.surgeryYear, surgeryYear);
		surgeryMonth = preferences.getInt(MainActivity.surgeryMonth, surgeryMonth);
		surgeryDay = preferences.getInt(MainActivity.surgeryDay, surgeryDay);
		textViewTimeAnswer.setText(surgeryDay + "/" + surgeryMonth + "/" + surgeryYear);
		textViewTimeAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Calendar calendar = Calendar.getInstance();
				
				new DatePickerDialog(UserProfileFragment.this.getActivity(), age, new DatePickerDialog.OnDateSetListener() 
				{
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						
						surgeryYear = year;
						surgeryMonth = monthOfYear + 1;
						surgeryDay = dayOfMonth;
						textViewTimeAnswer.setText(surgeryDay + "/" + surgeryMonth + "/" + surgeryYear);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
				
			}
		});
		
		return root;
	}

	@Override
	public void onStart() {

		super.onStart();

		Button button = (Button) getView().findViewById(R.id.button_profile_done);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				editor.putLong(MainActivity.preferencesCreated, new Date().getTime()).putInt(MainActivity.age, age).putInt(MainActivity.sex, sex)
				.putBoolean(MainActivity.isSurgeried, isSurgeried).putInt(MainActivity.leg, leg).putInt(MainActivity.surgeryDay, surgeryDay)
				.putInt(MainActivity.surgeryYear, surgeryYear).putInt(MainActivity.surgeryMonth, surgeryMonth).commit();
				upf.buttonClicked(R.layout.fragment_measure_method);

			}
		});
	}

}
