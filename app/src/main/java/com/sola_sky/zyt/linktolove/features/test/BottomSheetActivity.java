package com.sola_sky.zyt.linktolove.features.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sola_sky.zyt.linktolove.R;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetActivity extends AppCompatActivity {

    private ListView mLv;
    private ExampleAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        mLv = (ListView) findViewById(R.id.lv);
        mList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mList.add(i + " I Love You");
        }
        mAdapter = new ExampleAdapter(mList, this);
        mLv.setAdapter(mAdapter);
    }

    class ExampleAdapter extends BaseAdapter {

        private List<String> mList;
        private Context mContext;

        ExampleAdapter(List<String> list, Context context) {
            mList = list;
            mContext = context;
        }
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view_example
                , parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(getItem(position).toString());
            return convertView;
        }

        class ViewHolder {
            private TextView tv;
        }
    }
}
