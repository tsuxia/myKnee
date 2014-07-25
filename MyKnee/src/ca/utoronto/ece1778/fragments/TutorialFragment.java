package ca.utoronto.ece1778.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
			public void oneFingerLeft2Right(){
				
				if(flag_tutorial_page > 1)
					flag_tutorial_page -= 1;
				
				changeInformation(flag_tutorial_page);
			}
			
			public void oneFingerRight2Left(){
				
					flag_tutorial_page += 1;
				
				changeInformation(flag_tutorial_page);
				
			}
			
			private void changeInformation(int flag_tutorial_page){
				
				if (flag_tutorial_page == 1){
					
					textview_turotial_title.setText(R.string.title_tutorial_1);
					imageview_tutorial.setImageResource(R.drawable.tutorial_1);
					textview_turotial_description.setText(R.string.description_tutorial_1);
					
				}else if (flag_tutorial_page == 2){
					
					textview_turotial_title.setText(R.string.title_tutorial_2);
					imageview_tutorial.setImageResource(R.drawable.tutorial_2);
					textview_turotial_description.setText(R.string.description_tutorial_2);
					
				}else if (flag_tutorial_page == 3){
					
					textview_turotial_title.setText(R.string.title_tutorial_3);
					imageview_tutorial.setImageResource(R.drawable.tutorial_3);
					textview_turotial_description.setText(R.string.description_tutorial_3);
					
				}else if (flag_tutorial_page == 4){
					
					textview_turotial_title.setText(R.string.title_tutorial_4);
					imageview_tutorial.setImageResource(R.drawable.tutorial_4);
					textview_turotial_description.setText(R.string.description_tutorial_4);
					
				}else if (flag_tutorial_page == 5){
					
					textview_turotial_title.setText(R.string.title_tutorial_5);
					imageview_tutorial.setImageResource(R.drawable.tutorial_5);
					textview_turotial_description.setText(R.string.description_tutorial_5);
				}else if (flag_tutorial_page == 6){

					textview_turotial_title.setText(R.string.title_tutorial_6);
					imageview_tutorial.setImageResource(R.drawable.tutorial_6);
					textview_turotial_description.setText(R.string.description_tutorial_6);
				}else if (flag_tutorial_page == 7){
					
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
