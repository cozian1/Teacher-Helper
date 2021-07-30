package com.spectrum.task3.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Timepicker extends DialogFragment {
      @NonNull
      @Override
      @SuppressLint("NewApi")
      public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
         Calendar c = Calendar.getInstance();
         int hour = c.get(Calendar.HOUR_OF_DAY);
         int minute = c.get(Calendar.MINUTE);

         return new TimePickerDialog(getActivity(),
               (TimePickerDialog.OnTimeSetListener) getActivity(),
               hour, minute, DateFormat.is24HourFormat(getActivity()));
      }
}
