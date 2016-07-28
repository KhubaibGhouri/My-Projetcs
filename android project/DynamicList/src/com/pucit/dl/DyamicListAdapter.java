package com.pucit.dl;

import java.util.ArrayList;

import com.pucit.dl.R;
import com.pucit.dl.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DyamicListAdapter extends ArrayAdapter {
	private Context context;
	private ArrayList<Student> students;

	static class HolderView {
		public TextView tvName;
		public TextView tvDept;
		public TextView tvCGPA;
	}

	@SuppressWarnings("unchecked")
	public DyamicListAdapter(Context c, ArrayList<Student> students) {
		super(c, R.layout.list_item, students);
		this.context = c;
		this.students = students;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, parent, false);
			
			HolderView listItem = new HolderView();
			listItem.tvName = (TextView) convertView.findViewById(R.id.tvName);
			listItem.tvDept = (TextView) convertView.findViewById(R.id.tvDept);
			listItem.tvCGPA = (TextView) convertView.findViewById(R.id.tvCGPA);
			
			convertView.setTag(listItem);
		}

		HolderView listItem = (HolderView) convertView.getTag();

		Student s = students.get(position);
		
		listItem.tvName.setText(s.name);
		listItem.tvDept.setText(s.disp);
		listItem.tvCGPA.setText(s.act_str);

		return convertView;
	}
}
