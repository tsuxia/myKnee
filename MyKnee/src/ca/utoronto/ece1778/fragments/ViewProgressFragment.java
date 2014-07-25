package ca.utoronto.ece1778.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import ca.utoronto.ece1778.R;
import ca.utoronto.ece1778.interfaces.FragmentHandler;
import ca.utoronto.ece1778.services.Result;
import ca.utoronto.ece1778.services.ResultService;

public class ViewProgressFragment extends Fragment
{

	FragmentHandler mmfh = null;
	TextView textview_total_count = null;
	Button button_measure_again = null;
	Button button_view_chart = null;
	private ListView listview = null;
	private ResultService resultservice = null;
	private int total_times = 0;
	private String time_stamp = null;
	private String result_stamp = null;
	private int time_int = 0;
	private int result_degree = 0;
	private String result_whichleg = "left";
	int year = 0;
	int month = 0;
	int day = 0;
	int hour = 0;
	int minute = 0;
	int result_id = 0;

	@Override
	public void onAttach(Activity activity)
	{

		super.onAttach(activity);

		mmfh = (FragmentHandler) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.fragment_view_my_progress, container, false);

		return root;
	}

	@Override
	public void onStart()
	{

		super.onStart();

		// Read database and show on the list view
		resultservice = new ResultService(getActivity());

		listview = (ListView) getActivity().findViewById(R.id.listview_total_measurement);
		// set up list view items on click method
		listview.setOnItemClickListener(new ItemClickListener());
		textview_total_count = (TextView) getActivity().findViewById(R.id.textview_total_measurement);
		total_times = resultservice.getCount();

		textview_total_count.setText("You have measured " + total_times + " times!");

		// show the content of database on the list view
		show();

		button_measure_again = (Button) getActivity().findViewById(R.id.button_measure_again);
		button_measure_again.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				mmfh.buttonClicked(R.layout.fragment_measure_method);
			}
		});

	}

	private final class ItemClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{

			HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
			result_id = (Integer) item.get("id");
			// show a message while loader is loading
			AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
			adb.setTitle("Delete?");
			adb.setMessage("Do you want to delete ");
			// final int user_id = Integer.parseInt(v.getTag().toString());
			adb.setNegativeButton("Cancel", null);
			adb.setPositiveButton("Ok", new AlertDialog.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					resultservice.delete(result_id);
					mmfh.buttonClicked(R.layout.fragment_view_my_progress);
				}
			});
			adb.show();
		}
	}

	private void show()
	{
		List<Result> results = resultservice.getScrollData(0, 100);
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (Result result : results)
		{
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("id", result.getEvent_id());

			result_degree = result.getEvent_result();
			result_stamp = result_degree + "бу";
			item.put("result", result_stamp);

			time_int = result.getTime_int();
			year = time_int / (60 * 24 * 31 * 12);
			month = (time_int - year * 60 * 24 * 31 * 12) / (60 * 24 * 31);
			day = (time_int - year * 60 * 24 * 31 * 12 - month * 60 * 24 * 31) / (60 * 24);
			hour = (time_int - year * 60 * 24 * 31 * 12 - month * 60 * 24 * 31 - day * 60 * 24) / 60;
			minute = (time_int - year * 60 * 24 * 31 * 12 - month * 60 * 24 * 31 - day * 60 * 24 - hour * 60);
			time_stamp = "Date: " + year + "/" + month + "/" + day + " Time: " + hour + ":" + minute;
			item.put("time_int", time_stamp);
			
			result_whichleg = result.getWhich_leg();
			item.put("which_leg", result_whichleg);

			data.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.item, new String[] { "time_int", "result", "which_leg" }, new int[] { R.id.Textview_time, R.id.Textview_result, R.id.Textview_whichleg});

		listview.setAdapter(adapter);
	}

}
