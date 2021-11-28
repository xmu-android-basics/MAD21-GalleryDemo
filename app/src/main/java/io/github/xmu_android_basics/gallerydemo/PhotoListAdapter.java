package io.github.xmu_android_basics.gallerydemo;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

import io.github.xmu_android_basics.gallerydemo.databinding.PhotoListItemBinding;


public class PhotoListAdapter extends ListAdapter<PhotoItem, PhotoListAdapter.PhotoItemViewHolder> {


    protected PhotoListAdapter() {
        super(new PhotoItemDiff());
    }

    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhotoItemViewHolder holder = PhotoItemViewHolder.create(parent);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("PHOTO", getItem(holder.getAdapterPosition()));
                        Navigation.findNavController(holder.itemView)
                                .navigate(R.id.action_galleryFragment_to_photoFragment, bundle);

                    }
                }
        );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        PhotoItem photoItem = getItem(position);
        holder.bind(photoItem);

    }

    static class PhotoItemViewHolder extends RecyclerView.ViewHolder {
        private PhotoListItemBinding _binding;

        public PhotoItemViewHolder(@NonNull PhotoListItemBinding binding) {
            super(binding.getRoot());
            _binding = binding;
        }

        public static PhotoItemViewHolder create(@NonNull ViewGroup parent) {
            return new PhotoItemViewHolder(
                    PhotoListItemBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

        public void bind(PhotoItem photoItem) {
            Glide.with(itemView)
                    .load(photoItem.getPreviewUrl())
                    .placeholder(R.drawable.ic_photo_gray_24dp)
                    .into(_binding.photo);
        }
    }

    static class PhotoItemDiff extends DiffUtil.ItemCallback<PhotoItem> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull PhotoItem oldItem, @NonNull @NotNull PhotoItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull PhotoItem oldItem, @NonNull @NotNull PhotoItem newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }
    }
}

