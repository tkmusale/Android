package com.example.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.databinding.ComponentDataBinding;
import com.example.recyclerview.model.Component;

import java.util.ArrayList;
import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentHolder> {

    private Context mContext;
    private List<Component> mComponents = new ArrayList<>();

    public ComponentAdapter(Context context, List<Component> components) {
        this.mContext = context;
        this.mComponents = components;
    }

    @NonNull
    @Override
    public ComponentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComponentDataBinding componentDataBinding = DataBindingUtil.inflate(inflater, R.layout.item_row, parent, false);
        return new ComponentHolder(componentDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentHolder holder, int position) {
        holder.bind(mComponents.get(position));
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    public static class ComponentHolder extends RecyclerView.ViewHolder {

        private ComponentDataBinding componentDataBinding;

        public ComponentHolder(@NonNull ComponentDataBinding itemView) {
            super(itemView.getRoot());
            this.componentDataBinding = itemView;
        }

        public void bind(Component component) {
            componentDataBinding.setComponentList(component);
        }
    }

}
