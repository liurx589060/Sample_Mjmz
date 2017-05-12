package com.mjmz.lrx.sample_mjmz.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.Const;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.db.LiteOrmManager;
import com.mjmz.lrx.sample_mjmz.db.NotifyInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by liurunxiong on 2017/4/5.
 */

public class MyNotifyActivity extends BaseActivity {
    //控件类
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private Button mSelectedAllBtn;
    private Button mDeleteBtn;

    //数据类
    public static LiteOrm liteOrm;//lite-orm数据库
    private ArrayList<NotifyInstance> mDataList;
    private ArrayList<NotifyInstance> checkedList;//选中集合
    private boolean isEdit = false;//是否为编辑状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notify);

        //初始化
        init();

        Intent intent = getIntent();
        String data = "";

        Uri uri = intent.getData();
        if(uri != null) {
            data = uri.getQueryParameter("data");
        }
        ToastUtil.setToast(this,data);
    }

    /**
     * 初始化
     */
    private void init() {
        //获取数据库
        if (liteOrm == null) {
            liteOrm = LiteOrmManager.getLiteOrm(this,true);
        }

        //创建数据
        mDataList = new ArrayList<>();
        checkedList = new ArrayList<>();

        //获取所有数据
        ArrayList<NotifyInstance> list = liteOrm.query(NotifyInstance.class);
        mDataList.clear();
        mDataList.addAll(list);
        if(mDataList.size() == 0) {
            saveData2LiteOrm();
        }

        //找寻控件
        mDeleteBtn = (Button) findViewById(R.id.delete);
        mSelectedAllBtn = (Button) findViewById(R.id.select_all);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //设置数据和监听
        mAdapter = new MyRecyclerViewAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liteOrm.delete(checkedList);
                ArrayList<NotifyInstance> list = liteOrm.query(NotifyInstance.class);
                mDataList.clear();
                mDataList.addAll(list);
                mAdapter.notifyDataSetChanged();
            }
        });

        mSelectedAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList.clear();
                checkedList.addAll(mDataList);
                setEditStatus(!isEdit);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 保存数据到数据库
     */
    private void saveData2LiteOrm() {
        for (int i = 0 ; i < 5 ;i++) {
            NotifyInstance instance = new NotifyInstance();
            instance.Set_content("我的消息----" + i);

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            instance.Set_time(dateString);

            liteOrm.save(instance);
        }
        ToastUtil.setToast(this,"数据写入成功");
    }

    /**
     * adapter
     */
    private class MyRecyclerViewAdapter extends SlideInLeftAnimationAdapter {
        private ArrayList<NotifyInstance> adapterList;

        public MyRecyclerViewAdapter(ArrayList<NotifyInstance> list) {
            this.adapterList = list;
        }

        /**
         * 点选item
         * @param index   item的位置
         */
        public void setItemChecked(int index) {
            if(checkedList.contains(adapterList.get(index))) {
                checkedList.remove(adapterList.get(index));
            }else {
                checkedList.add(adapterList.get(index));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_notify_recyclerview_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
            ((MyViewHolder) holder).bindVH(position);
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView mContent;//活动内容
            private TextView mTime;//接受时间
            private ImageView mReaded;//是否读过标识图片
            private ImageView mChecked;//是否选中图片
            private View mCheckView;//编辑选项框

            public MyViewHolder(View itemView) {
                super(itemView);
                mChecked = (ImageView) itemView.findViewById(R.id.my_notify_listview_item_check_icon);
                mCheckView = itemView.findViewById(R.id.my_notify_listview_item_checkBox);
                mContent = (TextView) itemView.findViewById(R.id.my_notify_listview_item_content);
                mReaded = (ImageView) itemView.findViewById(R.id.my_notify_listview_item_flag);
                mTime = (TextView) itemView.findViewById(R.id.my_notify_listview_item_time);
            }

            public void bindVH(final int position) {
                NotifyInstance instance = adapterList.get(position);
                if(isEdit) {//编辑状态
                    mCheckView.setVisibility(View.VISIBLE);
                    if(checkedList.contains(adapterList.get(position))) {//选中项
                        mChecked.setVisibility(View.VISIBLE);
                    }else {//不是选中项
                        mChecked.setVisibility(View.GONE);
                    }
                }else {
                    mCheckView.setVisibility(View.GONE);
                }

                if(instance.Get_isReaded().equals("1")) {//是否点击读取过   1:为读取过     0:未读取过
                    mReaded.setImageResource(R.drawable.my_notify_listview_item_flag_true);
                }else {
                    mReaded.setImageResource(R.drawable.my_notify_listview_item_flag_false);
                }
                String content = instance.Get_content();
                mContent.setText(content);
                mTime.setText(instance.Get_time());

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isEdit) {//编辑状态
                            mAdapter.setItemChecked(position);
                            mAdapter.notifyDataSetChanged();
                        }else {
                            NotifyInstance instance = adapterList.get(position);
                            instance.Set_isReaded("1");//标记为读取过
                            //更新数据
                            liteOrm.update(instance, ConflictAlgorithm.Fail);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

                this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        setEditStatus(!isEdit);
                        return true;
                    }
                });
            }
        }
    }

    /**
     * 设置编辑状态，控件显示和隐藏
     * @param bool   是否为编辑状态
     */
    private void setEditStatus(boolean bool) {
        if(mAdapter == null) {
            return;
        }
        if(bool) {//编辑状态
            isEdit = true;//为编辑状态
            mAdapter.notifyDataSetChanged();
        }else {
            isEdit = false;
            checkedList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }
}
