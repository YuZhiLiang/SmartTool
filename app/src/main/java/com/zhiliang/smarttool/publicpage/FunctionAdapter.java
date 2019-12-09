package com.zhiliang.smarttool.publicpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhiliang.smarttool.DataBean.FunctionBean;
import com.zhiliang.smarttool.R;
import com.zhiliang.smarttool.agent.CommonListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.MainFunctionVH> {
    private LayoutInflater mLayoutInflater;
    private List<FunctionBean> mList;
    private CommonListener<FunctionBean> mListener;

    public FunctionAdapter() {
    }

    public FunctionAdapter(List<FunctionBean> list) {
        mList = list;
    }

    public List<FunctionBean> getList() {
        return mList;
    }

    public FunctionAdapter setList(List<FunctionBean> list) {
        mList = list;
        return this;
    }

    public CommonListener<FunctionBean> getListener() {
        return mListener;
    }

    public FunctionAdapter setListener(CommonListener<FunctionBean> listener) {
        mListener = listener;
        return this;
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
        FunctionBean functionBean = mList.get(position);
        holder.getTextView().setText(functionBean.getName());
        holder.getTextView().setOnClickListener(view -> {
            if (functionBean.getProcessMode() == FunctionBean.sProcessMode_Route) {
                ARouter.getInstance().build(functionBean.getPath()).navigation();
            } else {
                if (mListener != null) mListener.onExecutive(functionBean);
            }
        });
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
