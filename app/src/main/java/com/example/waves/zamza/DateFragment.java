package com.example.waves.zamza;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFragment extends DialogFragment {
    public static final String EXTRA_DATE = "com.example.waves.zamza.date";
    private static final String ARG_DATE = "date";
    private Date mDate;
    private DatePicker mDatePicker;
    private Button mPositiveButton;
    private Button mNegativeButton;

    public static DateFragment newInstance (Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE , date);

        DateFragment fragment = new DateFragment();
        fragment.setArguments(args);
        return fragment;
    }

@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState){
    Date date = (Date)getArguments().getSerializable(ARG_DATE);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    final int minutes = calendar.get(Calendar.MINUTE);

    View view = inflater.inflate(R.layout.dialog_date , null);


    mDatePicker = (DatePicker)view.findViewById(R.id.dialog_date_picker);
    mDatePicker.init(year , month, day , null);

    mPositiveButton = (Button) view.findViewById(R.id.save_button);
    mPositiveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int year = mDatePicker.getYear();
            int month = mDatePicker.getMonth();
            int day = mDatePicker.getDayOfMonth();
            Date date = new GregorianCalendar(year, month, day, hour, minutes).getTime();
            sendResult(Activity.RESULT_OK, date);
        }
    });

    mNegativeButton = (Button) view.findViewById(R.id.cancel_button);
    mNegativeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getTargetFragment() != null) {
                dismiss();
            } else {
                getActivity().finish();
            }
        }
    });
    return view;
}
    private void sendResult (int resultCode , Date date) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE , date);
            getTargetFragment().onActivityResult(getTargetRequestCode() , resultCode , intent);
            dismiss();
            return;

        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getActivity().setResult(resultCode, intent);
        getActivity().finish();
    }
}
