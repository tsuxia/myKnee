package ca.utoronto.ece1778.fragments;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ca.utoronto.ece1778.MainActivity;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.services.BenchmarkService;
import ca.utoronto.ece1778.services.BenchmarkService.Pair;
import ca.utoronto.ece1778.services.TempValue;

public class ViewBenchmarkFragment extends Fragment
{

	FragmentHandler fufh = null;
	boolean isSurgeried = false;
	int leg = 0;
	 
	@Override
	public void onAttach(Activity activity) 
	{
		
		super.onAttach(activity);

		fufh = (FragmentHandler)activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_view_benchmark, container, false);
		
		return root;
	}

	@Override
	public void onStart() 
	{
		super.onStart();
		
		TextView resultGet = (TextView)this.getView().findViewById(R.id.text_view_result_get);
		resultGet.setText(TempValue.result+"бу");
		
		TextView resultShould = (TextView)this.getView().findViewById(R.id.text_view_result_should);
		
		SharedPreferences preference = this.getActivity().getSharedPreferences(MainActivity.appName, this.getActivity().MODE_PRIVATE);
		
		isSurgeried = preference.getBoolean(MainActivity.isSurgeried, true);
		leg = preference.getInt(MainActivity.leg, -1);
		if(isSurgeried && (leg == UserProfileFragment.BOTH || leg == TempValue.leg))
		{
			int year = preference.getInt(MainActivity.surgeryYear, 1970);
			int month = preference.getInt(MainActivity.surgeryMonth, 1);
			int day = preference.getInt(MainActivity.surgeryDay, 1);
			Date sugeryDate = new Date();
			sugeryDate.setYear(year-1900);
			sugeryDate.setMonth(month-1);
			sugeryDate.setDate(day);
			Date currentDate = new Date();
			int days = (int) (currentDate.getTime() - sugeryDate.getTime())/(1000 * 60 * 60 * 24); 
			int weeks = days / 7;
			double benchmark = new BenchmarkService().getACLBenchmark(weeks);
			resultShould.setText(benchmark+"бу");
		} else
		{
			int age = preference.getInt(MainActivity.age, 0);
			Pair benchmark = new BenchmarkService().getNormalBenchmark(age);
			int min = (int) (benchmark.mean - benchmark.std);
			int max = (int) (benchmark.mean + benchmark.std);
			resultShould.setText(min + "бу" + "~" + max + "бу");
		}
		
		Button questionmarkButton = (Button)this.getView().findViewById(R.id.button_question_mark);
		questionmarkButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
		/*		if(isSurgeried && (leg == UserProfileFragment.BOTH || leg == TempValue.leg)){
					
						AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
						adb.setTitle("Reference for surgey leg");
						adb.setMessage("This information is based on the surgey date you entered " +
								"in the profile page and the data comes from the research by Boone DC; " +
								"Techniques of measurment of joint motion (Unpublished supplement to " +
								"Boone DC, and Azen, SP: Normal range of motion in male subjects. " +
								"J Bone Joint Surg Am 61:756, 1979.");
						adb.setNegativeButton("OK", null);
						adb.show();
					
				}else{*/
						
						AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
						adb.setTitle("Reference for benchmark");
						adb.setMessage("This information is based on your age and the benchmark " +
								"reference is: Normal hip and knee active range of motion: The " +
								"relationship to age. Roache KE, & Miles TP. Physical Therapy. 1991;71:656-665.");
						adb.setNegativeButton("OK", null);
						adb.show();
		//		}
			}
		});
		
		Button viewProgressButton = (Button)this.getView().findViewById(R.id.button_view_progress);
		viewProgressButton.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				fufh.buttonClicked(R.layout.fragment_view_my_progress);
			}
		});
		
	}
	
	
}
