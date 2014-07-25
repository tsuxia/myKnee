package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.listener.OnGestureListener;

public class TutorialFragment extends Fragment
{

	FragmentHandler tf = null;
	int flag_tutorial_page = 1;
	TextView textview_turotial_title = null;
	ImageView imageview_tutorial = null;
	TextView textview_turotial_description = null;
	
	@Override
	public void onAttach(Activity activity)
	{
		// TODO Auto-generated method stub
		super.onAttach(activity);
		tf = (FragmentHandler) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.fragment_tutorial, container,	false);
		return root;
	}

	@Override
	public void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		textview_turotial_title = (TextView)this.getActivity().findViewById(R.id.textview_turotial_title);
		imageview_tutorial = (ImageView)this.getActivity().findViewById(R.id.imageview_tutorial);
		textview_turotial_description = (TextView)this.getActivity().findViewById(R.id.textview_turotial_description);
		LinearLayout tutorialLayout = (LinearLayout)this.getActivity().findViewById(R.id.linearlayout_tutorial);
		tutorialLayout.setOnTouchListener(new OnGestureListener(this.getActivity()){
			
			@Override
			public void oneFingerLeft2Right()
			{
				// TODO Auto-generated method stub
				super.oneFingerLeft2Right();
				
				if (flag_tutorial_page == 1){
								
				}else if (flag_tutorial_page == 2){
					
					textview_turotial_title.setText("After surgery or injury you need to know how far your knee bends");
					imageview_tutorial.setImageResource(R.drawable.tutorial_1);
					textview_turotial_description.setText("myKnee the app designed by a physical therapist can measure that!");
					flag_tutorial_page = 1;
					
				}else if (flag_tutorial_page == 3){
					
					textview_turotial_title.setText("Attach to your ankle");
					imageview_tutorial.setImageResource(R.drawable.tutorial_2);
					textview_turotial_description.setText("");
					flag_tutorial_page = 2;
					
				}else if (flag_tutorial_page == 4){
					
					textview_turotial_title.setText("Start bending¡­");
					imageview_tutorial.setImageResource(R.drawable.tutorial_3);
					textview_turotial_description.setText("while lying on your stomach");
					flag_tutorial_page = 3;
					
				}else if (flag_tutorial_page == 5){
					
					textview_turotial_title.setText("Measure your bending");
					imageview_tutorial.setImageResource(R.drawable.tutorial_4);
					textview_turotial_description.setText("And compare to the benchmark for your age or surgery");
					flag_tutorial_page = 4;
				}
			}

			@Override
			public void oneFingerRight2Left()
			{
				// TODO Auto-generated method stub
				super.oneFingerRight2Left();
				
				if (flag_tutorial_page == 1){
					textview_turotial_title.setText("Attach to your ankle");
					imageview_tutorial.setImageResource(R.drawable.tutorial_2);
					textview_turotial_description.setText("");
					flag_tutorial_page = 2;
					
				}else if (flag_tutorial_page == 2){
					
					textview_turotial_title.setText("Start bending¡­");
					imageview_tutorial.setImageResource(R.drawable.tutorial_3);
					textview_turotial_description.setText("while lying on your stomach");
					flag_tutorial_page = 3;
					
				}else if (flag_tutorial_page == 3){
					
					textview_turotial_title.setText("Measure your bending");
					imageview_tutorial.setImageResource(R.drawable.tutorial_4);
					textview_turotial_description.setText("And compare to the benchmark for your age or surgery");
					flag_tutorial_page = 4;
					
				}else if (flag_tutorial_page == 4){
					
					textview_turotial_title.setText("View your progress over time");
					imageview_tutorial.setImageResource(R.drawable.tutorial_5);
					textview_turotial_description.setText("Let's start to measure!!");
					flag_tutorial_page = 5;
					
				}else if (flag_tutorial_page == 5){
					SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.appName, Context.MODE_PRIVATE);
					long preferencesCreated = preferences.getLong(MainActivity.preferencesCreated, 0);
					if (preferencesCreated == 0){
						
						tf.buttonClicked(R.layout.fragment_user_profile);
						
					}else{
						
						tf.buttonClicked(R.layout.fragment_welcome_second_user);
					
					}
				}
				
			}
			
		});
	}
	
}
