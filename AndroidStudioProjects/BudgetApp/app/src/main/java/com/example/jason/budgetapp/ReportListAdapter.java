package com.example.jason.budgetapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//class to create a custom listview
public class ReportListAdapter  extends BaseAdapter{

    //stores the name of the columns of the listview
    private static final String COL_ONE = "One";
    private static final String COL_TWO = "Two";
    private static final String COL_THREE = "Three";

    ViewHolder holder;

    //stores the list of items going into the list view
    public ArrayList<HashMap<String, String>> list;
    Activity activity;

    public ReportListAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //holds the textview of the listview columns
    private class ViewHolder{
        TextView firstTxt;
        TextView secondTxt;
        TextView thirdTxt;
    }

    //creates the new view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        //initilizes the new view
        if(convertView == null){
            convertView = inflater.inflate(R.layout.report_list_row, null);
            holder = new ViewHolder();
            holder.firstTxt = (TextView) convertView.findViewById(R.id.reportListColOneTxt);
            holder.secondTxt = (TextView) convertView.findViewById(R.id.reportListColTwoTxt);
            holder.thirdTxt = (TextView) convertView.findViewById(R.id.reportListColThreeTxt);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //starts adding all the elements to the listview
        HashMap<String, String> map = list.get(position);
        holder.firstTxt.setText(map.get(COL_ONE));
        holder.secondTxt.setText(map.get(COL_TWO));
        holder.thirdTxt.setText(map.get(COL_THREE));
        BudgetDB db = new BudgetDB(activity);
        Category temp = db.getCategoryByString(map.get(COL_ONE));
        //checks if the item in column three starts with $
        if(map.get(COL_THREE).charAt(0) == '$') {
            //checks if the amount stored in column 3 is greater than the category amount
            if (Double.parseDouble(map.get(COL_THREE).substring(2)) > temp.getAmount()) {
                //sets the text color to red to indicate the expenses are greater than the alloted amount
                holder.thirdTxt.setTextColor(activity.getResources().getColor(R.color.red));
            }
            else if(Double.parseDouble(map.get(COL_THREE).substring(2)) < 0){
                //sets the text color to green with there a extra amount for the category
                holder.thirdTxt.setTextColor(activity.getResources().getColor(R.color.green));
            }
            else {
                //sets the color to black if the amount is in the normal range
                holder.thirdTxt.setTextColor(activity.getResources().getColor(R.color.black));
            }
        }

        return convertView;
    }
}
