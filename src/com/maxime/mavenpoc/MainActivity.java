package com.maxime.mavenpoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class MainActivity extends SherlockFragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSherlock().getActionBar().setDisplayShowTitleEnabled(false);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.activity_main, menu);
		
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch (position) {
			case 0:
				Fragment fragment0 = new DummySectionFragment();
				Bundle args0 = new Bundle();
				args0.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment0.setArguments(args0);
				return fragment0;
			case 1:
				Fragment fragment1 = new FlowReaderSectionFragment();
				return fragment1;
			case 2:
				Fragment fragment2 = new DummySectionFragment();
				Bundle args2 = new Bundle();
				args2.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment2.setArguments(args2);
				return fragment2;

			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends SherlockFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}
	
	public static class FlowReaderSectionFragment extends SherlockListFragment {

		private static final String GIT_URL = "";
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			//TODO : setListAdapter(new MyListAdapter());
			String[] listString = {"Coucou","Moi","C'est","Maxime","Dugué"};
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getSherlockActivity(), android.R.layout.simple_list_item_1, new DownloadGitHubData().execute());
//			setListAdapter(adapter);
			new DownloadGitHubData().execute();
			return super.onCreateView(inflater, container, savedInstanceState);
		}

		
//		private List<String> getFlow() {
//			List<String> flowList = new ArrayList<String>();
//			RepositoryService service = new RepositoryService();
//			try {
//				for(Repository repo : service.getRepositories("AerandirSurion")){
//					flowList.add(repo.getName());
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//				Log.e("getFlow", "error while accessing github account");
//			}
//			return flowList;
//		}

		
		private class DownloadGitHubData extends AsyncTask<Void, Void, List<String>>{

			@Override
			protected List<String> doInBackground(Void... params) {
				List<String> flowList = new ArrayList<String>();
				RepositoryService service = new RepositoryService();
				try {
					for(Repository repo : service.getRepositories("AerandirSurion")){
						flowList.add(repo.getName());
					}
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("getFlow", "error while accessing github account");
				}
				return flowList;
			}
			
			@Override
			protected void onPostExecute(List<String> result){
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getSherlockActivity(), android.R.layout.simple_list_item_1, result);
				setListAdapter(adapter);
			}
			
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
		}
		
	}
	
	
	

}
