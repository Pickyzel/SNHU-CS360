package com.assignment.project3_tiffanymcdonnell;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ProductViewHolder> {

    private List<Item> Itemlist;
    private OnItemClickListener listener;
    private OnItemDeleteClickListener deleteListener;

    public ItemAdapter(List<Item> Itemlist, OnItemClickListener listener, OnItemDeleteClickListener deleteListener) {
        this.Itemlist = Itemlist;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Item product = Itemlist.get(position);
        holder.textViewProductId.setText(product.getItemName());
        holder.textViewProductDesc.setText(product.getItemDesc());
        holder.textViewProductQuantity.setText(String.valueOf(product.getCount()));

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method from the activity or fragment to handle the deletion
                if (deleteListener != null) {
                    deleteListener.onItemDelete(product, position);
                }
            }
        });

        // Set the click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use the interface to pass the click event up to the Activity
                if (listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
    }


    public interface OnItemDeleteClickListener {
        void onItemDelete(Item product, int position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductId, textViewProductDesc, textViewProductQuantity;
        Button buttonDelete;

        ProductViewHolder(View itemView) {
            super(itemView);
            textViewProductId = itemView.findViewById(R.id.itemName);
            textViewProductDesc = itemView.findViewById(R.id.itemDesc);
            textViewProductQuantity = itemView.findViewById(R.id.Count);
            buttonDelete = itemView.findViewById(R.id.Deletebtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Use the interface to pass the click event up to the Activity
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(Itemlist.get(position));
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return Itemlist.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Item product);
    }

    public void removeItem(int position) {
        Itemlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Itemlist.size());
    }

}