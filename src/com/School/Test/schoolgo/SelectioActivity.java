package com.School.Test.schoolgo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectioActivity extends Activity {
	//private Classification data;
	 private ListView listView;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO �Զ����ɵķ������
	super.onCreate(savedInstanceState);
	setContentView(R.layout.selectioactivity);
	//data.CreateSingle();
	 listView =(ListView) findViewById(R.id.lv);
     listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
     setContentView(listView);
}
private List<String> getData(){
    
    List<String> data = new ArrayList<String>();
    data.add("����");
    data.add("����");
    data.add("����");
    data.add("����");
     
    return data;
}
}
