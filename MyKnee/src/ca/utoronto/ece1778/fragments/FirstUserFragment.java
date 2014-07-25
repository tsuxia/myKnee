package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;

public class FirstUserFragment extends Fragment
{

	FragmentHandler fufh = null;
	SharedPreferences preferences = null;
	Editor editor = null;
	int flag_first_install;
	
	@Override
	public void onAttach(Activity activity)
	{

		super.onAttach(activity);

		fufh = (FragmentHandler) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_welcome_first_user, container, false);
		preferences = this.getActivity().getSharedPreferences(MainActivity.appName, Activity.MODE_PRIVATE);
		editor = preferences.edit();
		flag_first_install = preferences.getInt(MainActivity.first_install, 0);
		
		return root;
	}

	@Override
	public void onStart()
	{

		super.onStart();

		Button startButton = (Button) getView().findViewById(R.id.button_welcome_first_done);
		startButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				 if (flag_first_install == 0){
					 
					 editor.putInt(MainActivity.first_install, 1).commit();
					 fufh.buttonClicked(R.layout.fragment_tutorial);		 
					 
				 }else{
					 
					 fufh.buttonClicked(R.layout.fragment_user_profile);
					 
				 }
			}
		});
	}

}
