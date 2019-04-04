package com.jack.helloen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jack.helloen.R;
import com.jack.helloen.bean.ItemClass;
import com.jack.helloen.util.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {
    private ListView list_class;
    private CustomAdapter<ItemClass> adapter = null;
    private List<ItemClass> mData = null;
    private final int CODE = 123;
    private ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //设置首页标题
        ((TextView) findViewById(R.id.title_text)).setText("列表");

        patchView();
        bindView();
    }

    private void patchView() {
        //ListView兼容ScrollView
        sv = (ScrollView) findViewById(R.id.main_scroll);
        sv.smoothScrollTo(0, 0);
    }

    private void bindView() {
        list_class = (ListView) findViewById(R.id.list_class);

        mData = new ArrayList<>();
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a001", "Hope to see you again next year"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a002", "What's new with you"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a003", "Nice to see you again"));

        mData.add(new ItemClass(R.mipmap.ic_launcher, "a004", "He's out at the moment"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a005", "What's the weather forecast for today"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a007", "No need to change"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a008", "It's just a couple of minutes"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a009", "Having a date"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a010", "Birthday gift"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a011", "Will you make some recommendation"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a012", "Payment"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a013", "Complain about food"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a014", "I'm not feeling well"));
        mData.add(new ItemClass(R.mipmap.ic_launcher, "a015", "My whole body feels weak"));

        adapter = new CustomAdapter<ItemClass>((ArrayList) mData, R.layout.item_list) {
            @Override
            public void bindView(ViewHolder holder, ItemClass obj) {
                holder.setImageResource(R.id.img_icon, obj.getcIcon());
                holder.setText(R.id.txt_aIndex, "" + obj.getcIndex());
                holder.setText(R.id.txt_aTitle, obj.getcTitle());
            }
        };
        list_class.setAdapter(adapter);
        list_class.setOnItemClickListener(listener);
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(ListActivity.this, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();

            ItemClass item = mData.get(position);
            Intent intent = new Intent(ListActivity.this, PlayerActivity.class);
            intent.putExtra("index", item.getcIndex());
            intent.putExtra("title", item.getcTitle());
            startActivityForResult(intent, CODE);
        }
    };
}
