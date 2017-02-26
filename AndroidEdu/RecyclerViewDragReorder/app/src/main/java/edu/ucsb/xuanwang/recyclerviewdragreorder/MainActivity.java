package edu.ucsb.xuanwang.recyclerviewdragreorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    /**
     * 数据源
     */
    private List<UserInfo> userInfoList = null;
    /**
     * 数据适配器
     */
    private MainAdapter mainAdapter;
    /**
     * 滑动拖拽的帮助类
     */
    private DefaultItemTouchHelper itemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_update).setOnClickListener(onClickListener);

        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 必须要设置一个布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        mainAdapter = new MainAdapter(userInfoList);
        mainAdapter.setOnItemClickListener(onItemClickListener);
        mainAdapter.setOnCheckedChangeListener(onCheckedChangeListener);
        recyclerView.setAdapter(mainAdapter);

        // 模拟数据
        userInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            boolean isCheck = i % 2 == 0;
            userInfoList.add(new UserInfo("名字" + i, isCheck ? "男" : "女", isCheck));
        }
        mainAdapter.notifyDataSetChanged(userInfoList);


        // 把ItemTouchHelper和itemTouchHelper绑定
        itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mainAdapter.setItemTouchHelper(itemTouchHelper);

        itemTouchHelper.setDragEnable(true);
        itemTouchHelper.setSwipeEnable(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_update) {
                userInfoList.get(3).setName("NoHttp真好用");
                userInfoList.get(3).setCheck(false);
                mainAdapter.notifyDataSetChanged();
            }
        }
    };

    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (userInfoList != null) {
                userInfoList.remove(adapterPosition);
                mainAdapter.notifyItemRemoved(adapterPosition);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (userInfoList != null) {
                // 更换数据源中的数据Item的位置
                Collections.swap(userInfoList, srcPosition, targetPosition);

                // 更新UI中的Item的位置，主要是给用户看到交互效果
                mainAdapter.notifyItemMoved(srcPosition, targetPosition);
                return true;
            }
            return false;
        }
    };

    /**
     * RecyclerView的Item点击监听
     */
    private MainAdapter.OnItemClickListener onItemClickListener = new MainAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(MainActivity.this, "第" + position + "被点击", Toast.LENGTH_SHORT).show();
        }
    };

    private MainAdapter.OnCheckedChangeListener onCheckedChangeListener = new MainAdapter.OnCheckedChangeListener() {
        @Override
        public void onItemCheckedChange(CompoundButton view, int position, boolean checked) {
            Toast.makeText(MainActivity.this, "第" + position + (checked ? "选中" : "被反选"), Toast.LENGTH_SHORT).show();
        }
    };
}

