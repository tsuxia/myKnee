package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.services.TempValue;

public class MeasureMethodFragment extends Fragment {
	
	FragmentHandler mmfh = null;
	
	ImageView onStomach = null;
	
	Button buttonLeft = null;
	Button buttonRight = null;
	int sex = 0;
	
	public static final int ON_STOMATCH = 1;
	public static final int ON_BACK = 2;
	
	int measureMethod = ON_STOMATCH;
    int flag = 0;
	
	@Override
	public void onAttach(Activity activity) 
	{
		
		super.onAttach(activity);

		mmfh = (FragmentHandler)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_measure_method, container, false);
		buttonLeft = (Button)root.findViewById(R.id.button_left);
		buttonRight = (Button)root.findViewById(R.id.button_right);
		
		// show a message while loader is loading
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
	//	adb.setTitle("Delete?");
		adb.setMessage("Which leg are you measuring?");
		adb.setNegativeButton("Left", new AlertDialog.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				TempValue.leg = UserProfileFragment.LEFT;
				buttonLeft.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonRight.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
	    });
		
		adb.setPositiveButton("Right", new AlertDialog.OnClickListener()
		{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					TempValue.leg = UserProfileFragment.RIGHT;
					buttonLeft.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
					buttonRight.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				}
		});
		adb.show();
		
		
		onStomach = (ImageView)root.findViewById(R.id.image_view_on_stomach);
		onStomach.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				measureMethod = MeasureMethodFragment.ON_STOMATCH;
				onStomach.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				flag = 0;
			}
		});
		
		buttonLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				TempValue.leg = UserProfileFragment.LEFT;
				buttonLeft.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonRight.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		buttonRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				TempValue.leg = UserProfileFragment.RIGHT;
				buttonRight.setBackgroundColor(UserProfileFragment.COLOR_SETTED);
				buttonLeft.setBackgroundColor(UserProfileFragment.COLOR_UNSET);
			}
		});
		
		return root;
	}
	
	@Override
	public void onStart() 
	{
		
		super.onStart();
		
		Button button = (Button)getView().findViewById(R.id.button_measure_method_done);
		button.setOnClickListener(new View.OnClickListener() 
		{		
			@Override
			public void onClick(View arg0) 
			{
					mmfh.buttonClicked(R.layout.fragment_measurement);
			}
		});
	}

}
