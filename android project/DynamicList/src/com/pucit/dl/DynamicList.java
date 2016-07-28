package com.pucit.dl;

import java.io.File;
import java.util.ArrayList;

import com.pucit.dl.DyamicListAdapter;
import com.pucit.dl.Student;

import android.app.Activity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.DynamicLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DynamicList extends Activity implements OnItemClickListener, OnClickListener, OnItemLongClickListener {
	private ListView dynamicList;
	private ArrayList<Student> students;
	private DyamicListAdapter dla;
	
	private TextView tvTitle;
	private ImageButton imageButton1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;
	private ImageButton btn5;
	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	
	private Button btnInsert;
	
	private SQLiteDatabase studentDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_list);
		
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + getApplicationContext().getPackageName() + "/Files"); 
		if (!mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				return ;
			}
		} 
		File dbFile = new File(mediaStorageDir.getPath() + File.separator + "stddb.db");
		studentDB = openOrCreateDatabase(dbFile.getPath(), MODE_PRIVATE, null);
		studentDB.execSQL("CREATE TABLE IF NOT EXISTS students(stdID INTEGER PRIMARY KEY AUTOINCREMENT, pkgName TEXT, disp TEXT, act_str TEXT, company TEXT);");
		
		students = selectAllStudents();
		dla = new DyamicListAdapter(this, students);
		dynamicList = (ListView) findViewById(R.id.lvStudents);
		dynamicList.setAdapter(dla);
		dynamicList.setOnItemClickListener(this);
		dynamicList.setOnItemLongClickListener(this);
		
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		btn2 = (ImageButton) findViewById(R.id.imageButton2);
		btn3 = (ImageButton) findViewById(R.id.imageButton3);
		btn4 = (ImageButton) findViewById(R.id.imageButton4);
		btn5 = (ImageButton) findViewById(R.id.imageButton5);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		iv2 = (ImageView) findViewById(R.id.imageView2);
		iv3 = (ImageView) findViewById(R.id.imageView3);
		
		
		btnInsert = (Button) findViewById(R.id.btnInsert);
		btnInsert.setOnClickListener(this);
		
		imageButton1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		iv1.setOnClickListener(this);
		
		homeScreenView();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Student s = students.get(position);
		Toast toast = Toast.makeText(this, s.name, 1);
		toast.show(); 
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		//showListDialog(position);
		return false;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnInsert: {
			Student s = new Student();
			Cursor c = studentDB.rawQuery("SELECT MAX(stdID) from students", null);
			c.moveToFirst();
			int id = c.getInt(0)+1;
			s.id = id;
			s.name = "ali farooq " + id;
			s.disp = id + "this is great pkg 4u";
			s.act_str = "*101*1*02#";
			s.company = "jazz";
			insertStudent(s);
			students.add(s);
			dla.notifyDataSetChanged();
		}
		break;
		
		case R.id.imageButton1: {
			catagoryScreen();
		}
		break;
	
		case R.id.imageView1:{
			showList();
		}
		break;

		
		default: break;
		}
	}
	

	private void insertStudent(Student s) {
		studentDB.execSQL("INSERT INTO students VALUES(NULL, '"+s.name+"', '"+s.disp+"', '"+s.act_str+"', '"+s.company+"' );");
	}
	
//	private void deleteStudent(Student s) {
//		studentDB.execSQL("DELETE FROM students WHERE stdID = "+s.id+"");
//	}
//	
//	private void updateStudent(Student s) {
//		studentDB.execSQL("UPDATE students SET stdName = '"+s.name+"', stdDept = '"+s.dept+"', stdCGPA = '"+s.cgpa+"' where stdID = "+s.id+"");
//	}
//	
	private ArrayList<Student> selectAllStudents() {
		ArrayList<Student> ss = new ArrayList<Student>();
		Cursor c = studentDB.rawQuery("SELECT stdID, pkgName, disp, act_str, company from students", null);
		c.moveToFirst();		
		for (int i = 0; i < c.getCount(); i++) {
			Student s = new Student();
			s.id = c.getInt(0);
			s.name = c.getString(1);
			s.disp = c.getString(2);
			s.act_str = c.getString(3);
			s.company = c.getString(4);
			ss.add(s);
			c.moveToNext();
		}
		return ss;
	}	
//	private void showListDialog(final int position) {
//		AlertDialog.Builder alert = new AlertDialog.Builder(this);
//		String[] list = {"Delete", "Update"};
//		alert.setItems(list,  new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				if (which == 0) {
//					Student s = students.get(position);
//					deleteStudent(s);
//					students.remove(position);
//					dla.notifyDataSetChanged();
//				}
//				else if (which == 1) {
//					Student s = students.get(position);
//					s.name = "Default Name";
//					s.dept = "Default Dept";
//					s.cgpa  = 0.0f;
//					updateStudent(s);
//					dla.notifyDataSetChanged();
//				}
//			}
//		});
//		alert.show();
//	}

	private void catagoryScreen() {
		tvTitle.setText("please select one");
		imageButton1.setVisibility(View.GONE);
		btn2.setVisibility(View.GONE);
		btn3.setVisibility(View.GONE);
		btn4.setVisibility(View.GONE);
		btn5.setVisibility(View.GONE);
		btnInsert.setVisibility(View.GONE);
		iv1.setVisibility(View.VISIBLE);
		iv2.setVisibility(View.VISIBLE);
		iv3.setVisibility(View.VISIBLE);
	}
	
	private void homeScreenView(){
		iv1.setVisibility(View.GONE);
		iv2.setVisibility(View.GONE);
		iv3.setVisibility(View.GONE);
		dynamicList.setVisibility(View.GONE);
		btn2.setVisibility(View.VISIBLE);
		btn3.setVisibility(View.VISIBLE);
		btn4.setVisibility(View.VISIBLE);
		btn5.setVisibility(View.VISIBLE);	
		btnInsert.setVisibility(View.GONE);
		imageButton1.setVisibility(View.VISIBLE);
	}
	
	private void showList(){
		tvTitle.setText("List of pkgs");
		imageButton1.setVisibility(View.GONE);
		btn2.setVisibility(View.GONE);
		btn3.setVisibility(View.GONE);
		btn4.setVisibility(View.GONE);
		btn5.setVisibility(View.GONE);
		iv1.setVisibility(View.GONE);
		iv2.setVisibility(View.GONE);
		iv3.setVisibility(View.GONE);
		btnInsert.setVisibility(View.VISIBLE);
		dynamicList.setVisibility(View.VISIBLE);
	}
}
