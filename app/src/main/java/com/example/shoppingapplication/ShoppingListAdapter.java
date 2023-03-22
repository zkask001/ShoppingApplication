//package com.example.shoppingapplication;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {
//    private List<ShoppingList> shoppingLists;
//    private Context context;
//    private OnItemClickListener listener;
//
//    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists) {
//        this.context = context;
//        this.shoppingLists = shoppingLists;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
//
////    @Override
////    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
//////        return new ViewHolder(view);
////    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        ShoppingList shoppingList = shoppingLists.get(position);
//        holder.textViewName.setText(shoppingList.getName());
//        holder.textViewItemCount.setText(String.valueOf(shoppingList.getItems().size()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return shoppingLists.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewName;
//        TextView textViewItemCount;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
////            textViewName = itemView.findViewById(R.id.text_view_name);
////            textViewItemCount = itemView.findViewById(R.id.text_view_item_count);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
//}
