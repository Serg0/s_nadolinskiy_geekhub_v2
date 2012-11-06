package com.example.geekhub_3rd_homework;




import java.util.ArrayList;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TitlesFragment extends Fragment {
	ArrayList<Content> aLocal;
	ArrayList<String> titleString = new ArrayList<String>();
	ArrayList<String> contentString = new ArrayList<String>();
	
	String[] names = { "alpha", "bravo", "charlie", "delta", "eco"};
	
	public TitlesFragment(ArrayList<Content> array) {
		// TODO Auto-generated constructor stub
		aLocal = array;
		for (Content v:aLocal)
	    {
			titleString.add(v.title);
			contentString.add(v.content);
//			Toast.makeText(getActivity(), titleString.toString(), titleString.size());
	    }
	
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	//	return super.onCreateView(R.layout., null);
		return inflater.inflate(R.layout.fragment_titles, null);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		ListView lvMain = (ListView) getView().findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titleString);
	    lvMain.setAdapter(adapter);
	    
	    lvMain.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	//startActivity(new Intent(getActivity(), SecondActivity.class));
	        	
	        	
	        	Intent intent = new Intent(getActivity(), SecondActivity.class); 
	            intent.putExtra("content", contentString.get(position));
	           
	            startActivity(intent);
	        	//Toast.makeText(getActivity(), parent.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
	        	
	        }

			
	    });
		super.onActivityCreated(savedInstanceState);
	}

}
