package com.pulsa.nav_bottom.homeAtribut;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.setGet.DataMenu;
import com.pulsa.transaksi.voucherGame;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AndroidDataAdapter extends RecyclerView.Adapter<AndroidDataAdapter.ViewHolder> {
    private ArrayList<DataMenu> arrayList;
    private Context mcontext;

    public AndroidDataAdapter(Context context, ArrayList<DataMenu> android) {
        this.arrayList = android;
        this.mcontext = context;
    }

    public AndroidDataAdapter(voucherGame context, ArrayList<DataMenu> menu) {
        this.arrayList = menu;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(AndroidDataAdapter.ViewHolder holder, int i) {
        holder.textView.setText(arrayList.get(i).getJudul());
        if (arrayList.get(i).getImage() == null || arrayList.get(i).getImage().length() == 0) {
            Glide.with(mcontext)
                    .load(getImageDrawable(arrayList.get(i).getJudul()))
                    .into(holder.imageView);
        } else {
            Glide.with(mcontext)
                    .load(arrayList.get(i).getImage())
                    .apply(new RequestOptions().placeholder(getImageDrawable(arrayList.get(i).getImage())))
                    .into(holder.imageView);
        }
        //Glide.with(mcontext).load(getImageDrawable(arrayList.get(i).getJudul())).into(holder.imageView);
    }

    private Drawable getImageDrawable(String judul) {
        String namaImage = "menu/"+judul;
        try {
            InputStream ims = mcontext.getAssets().open(namaImage);
            return Drawable.createFromStream(ims, null);
        } catch (Exception e) {
            Log.e("error img", e.getMessage());
            InputStream ims = null;
            try {
                ims = mcontext.getAssets().open("no_image.png");
                return Drawable.createFromStream(ims, null);
            } catch (IOException e1) {
                Log.e("error img", e.getMessage());
                return null;
            }

        }

    }

    @Override
    public AndroidDataAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {
        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.row_layout, vGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.text);
            imageView = v.findViewById(R.id.image);
        }
    }
}
