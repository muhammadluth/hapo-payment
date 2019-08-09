package com.pulsa.nav_bottom.inboxAtribut;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.utils.ConvertTanggal;

public class TodoCursorAdapter extends CursorAdapter {

    public TodoCursorAdapter(FragmentActivity favorite, Cursor todoCursor) {
        super(favorite, todoCursor);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_inbox, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView ISI = (TextView) view.findViewById(R.id.isi);
        TextView TGL = (TextView) view.findViewById(R.id.tgl);
        // Extract properties from cursor
        String isi = cursor.getString(cursor.getColumnIndexOrThrow("isi"));
        String tgl = cursor.getString(cursor.getColumnIndexOrThrow("tgl"));
        // Populate fields with extracted properties
        ISI.setText(isi);
        TGL.setText(ConvertTanggal.tanggal(tgl));
    }
}
