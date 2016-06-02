package com.sola_sky.zyt.linktolove.features;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sola_sky.zyt.linktolove.R;

import java.util.Collections;
import java.util.List;

public class RecyActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecycler;
    private ItemTouchHelper mHelper;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mRecycler = (RecyclerView) findViewById(R.id.recy);

        mHelper = new ItemTouchHelper(new Callback());

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mHelper.attachToRecyclerView(mRecycler);
    }

    class Callback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int swipeFlags = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = viewHolder.getAdapterPosition();
            if (fromPosition > toPosition) {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mDatas, i, i - 1);
                }
            } else {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mDatas, i, i + 1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }

    class RecyAdaper extends RecyclerView.Adapter<RecyAdaper.MyHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(RecyActivity.this)
                    .inflate(R.layout.item_view, parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class MyHolder extends RecyclerView.ViewHolder {

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
