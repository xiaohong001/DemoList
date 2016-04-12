package me.honge.demo09_password.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import me.honge.demo09_password.R;
import me.honge.demo09_password.adapter.BaseRecyclerAdapter;
import me.honge.demo09_password.adapter.RecyclerViewHolder;
import me.honge.demo09_password.data.DataManager;
import me.honge.demo09_password.data.config.Constans;
import me.honge.demo09_password.data.model.Group;
import me.honge.demo09_password.data.model.Password;
import me.honge.demo09_password.ui.base.BaseActivity;
import rx.Subscriber;

public class AddActivity extends BaseActivity {

    private static final String TAG = AddActivity.class.getSimpleName();
    int i = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button add = (Button) findViewById(R.id.btAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                DataManager.getInstance().insertPassword(new Password.Builder()
                        .name("hello" + i)
                        .accountId(1)
                        .groupId(1)
                        .password("password")
                        .createDate(System.currentTimeMillis())
                        .updateDate(System.currentTimeMillis())
                        .build());

                showGroups();
            }
        });
    }

    private void showGroups(){
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_group_list,null);
        RecyclerView rvGroup = (RecyclerView) view.findViewById(R.id.rvGroups);
        rvGroup.setLayoutManager(new LinearLayoutManager(this));

        List<Group> groups = new ArrayList<>();
        final BaseRecyclerAdapter<Group> adapter = new BaseRecyclerAdapter<Group>(this,groups) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, Group item) {
                holder.setText(android.R.id.text1,item.mName);
                holder.itemView.setTag(item);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constans.DEBUG) {
                            Log.e(TAG, "点击了");
                        }
                    }
                });
            }
        };
        rvGroup.setAdapter(adapter);
        dialog.setContentView(view);


        DataManager.getInstance().getGroupList().subscribe(new Subscriber<List<Group>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Group> groups) {
                for (int i=0;i<1;i++){
                Group group = new Group();
                group.mName = "group"+i;
                groups.add(group);
            }
                adapter.setData(groups,true);
                dialog.show();
            }
        });

    }
}
