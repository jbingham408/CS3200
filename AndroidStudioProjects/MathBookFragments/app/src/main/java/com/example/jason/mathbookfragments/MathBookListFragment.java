package com.example.jason.mathbookfragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by jason on 11/12/15.
 */
public class MathBookListFragment extends ListFragment {
    MainActivity mainAct;
    int bookSelection = 0;
    private String[] isbns;

    @Override
    public void onInflate(Activity act, AttributeSet attrs, Bundle icicle) {
        super.onInflate(act, attrs, icicle);
    }

    @Override
    public void onAttach(Activity act) {
        super.onAttach(act);

        mainAct = (MainActivity) act;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    @Override
    public View onCreateView(LayoutInflater myInflater, ViewGroup container, Bundle icicle) {
        return super.onCreateView(myInflater, container, icicle);
    }

    @Override
    public void onActivityCreated(Bundle icicle) {
        super.onActivityCreated(icicle);

        ArrayAdapter<CharSequence> adptr = ArrayAdapter.createFromResource(getActivity(),
                R.array.isbns,
                android.R.layout.simple_list_item_1);
        this.setListAdapter(adptr);

        ListView listview = this.getListView();
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setSelection(bookSelection);

        mainAct.displayBookText(bookSelection);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        bookSelection = pos;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainAct = null;
    }
}
