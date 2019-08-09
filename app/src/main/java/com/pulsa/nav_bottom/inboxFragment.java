package com.pulsa.nav_bottom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pulsa.R;
import com.pulsa.db.dbinbox;
import com.pulsa.nav_bottom.inboxAtribut.TodoCursorAdapter;
import com.pulsa.nav_bottom.inboxAtribut.inboxDetail;

import java.util.ArrayList;
import java.util.List;

public class inboxFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public static ListView mList;
    public static Cursor mCursor;
    public static dbinbox mHelper;
    public static TodoCursorAdapter dataAdapter;
    LinearLayout doodle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.inbox, container, false);
        mList = (ListView) v.findViewById(R.id.list);

        doodle = (LinearLayout) v.findViewById(R.id.doodle);
        ImageView doodle_img = (ImageView) v.findViewById(R.id.doodle_img);
        TextView doodle_keterangan = (TextView) v.findViewById(R.id.doodle_keterangan);
        doodle_img.setImageDrawable(getResources().getDrawable(R.drawable.go_inbox));
        doodle_keterangan.setText(getResources().getString(R.string.doodle_iput_no_inbox));
        doodle.setVisibility(View.GONE);

        mList.setOnItemClickListener(this);
        mList.setOnItemLongClickListener(this);
        mHelper = new dbinbox(getActivity());
        jalan();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        jalan();
    }

    public void jalan() {
        mCursor = mHelper.getAllDataInbox();
        if (mCursor.getCount() > 0) {
            doodle.setVisibility(View.GONE);
        } else {
            doodle.setVisibility(View.VISIBLE);
        }
        dataAdapter = new TodoCursorAdapter(getActivity(), mCursor);
        // Assign adapter to ListView
        mList.setAdapter(dataAdapter);
    }

    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        mCursor.moveToPosition(position);
        final String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("Buka");
        mAnimals.add("Hapus");
        mAnimals.add("Hapus Semua");
        mAnimals.add("Tutup");
        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
                if (selectedText.equals("Buka")) {
                    String dari = rowId;
                    Intent intent = new Intent(getActivity(), inboxDetail.class);
                    intent.putExtra("id", dari);
                    startActivity(intent);
                } else if (selectedText.equals("Hapus")) {
                    mHelper.Hapus(rowId);
                } else if (selectedText.equals("Hapus Semua")) {
                    mHelper.HapusALL();
                }
                jalan();
                dialog.dismiss();
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        mCursor.moveToPosition(position);
        final String rowId = mCursor.getString(0); //Column 0 of the cursor is the id
        String dari = rowId;
        Intent intent = new Intent(getActivity(), inboxDetail.class);
        intent.putExtra("id", dari);
        startActivity(intent);
    }
}
