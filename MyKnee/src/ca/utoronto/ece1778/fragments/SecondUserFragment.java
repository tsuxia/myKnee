package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;

public class SecondUserFragment extends Fragment {
	
	FragmentHandler fufh = null;
	 
	@Override
	public void onAttach(Activity activity) 
	{
		
		super.onAttach(activity);

		fufh = (FragmentHandler)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_welcome_second_user, container, false);
		
		return root;
	}

	@Override
	public void onStart() 
	{
		
		super.onStart();
		
		Button startButton = (Button)getView().findViewById(R.id.button_welcome_second_done);
		startButton.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
			
				fufh.buttonClicked(R.layout.fragment_measure_method);
				
			}
		});
		

	}

}
