package com.zhiliang.smarttool.publicpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhiliang.smarttool.DataBean.MainFunction;
import com.zhiliang.smarttool.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainFunctionAdapter extends RecyclerView.Adapter<MainFunctionAdapter.MainFunctionVH> {
    private LayoutInflater mLayoutInflater;
    private List<MainFunction> mList;

    public MainFunctionAdapter() {
    }

    public MainFunctionAdapter(List<MainFunction> list) {
        mList = list;
    }

    public List<MainFunction> getList() {
        return mList;
    }

    public void setList(List<MainFunction> list) {
        mList = list;
    }

    @NonNull
    @Override
    public MainFunctionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new MainFunctionVH(mLayoutInflater.inflate(R.layout.main_function_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainFunctionVH holder, int position) {
        MainFunction mainFunction = mList.get(position);
        holder.getTextView().setText(mainFunction.getnName());
        holder.getTextView().setOnClickListener(view -> ARouter.getInstance().build(mainFunction.getPath()).navigation());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class MainFunctionVH extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MainFunctionVH(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
