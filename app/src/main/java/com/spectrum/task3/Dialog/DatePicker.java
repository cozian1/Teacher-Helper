package com.spectrum.task3.Dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePicker extends DialogFragment {

   @NonNull
   @Override
   @SuppressLint("NewApi")
   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      // Get the current time and use it as the default values for the picker
      Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);
      int hour = c.get(Calendar.HOUR_OF_DAY);
      int minute = c.get(Calendar.MINUTE);

      // Create and return an instance of the DatePickerDialog
      return new DatePickerDialog(getActivity(),
            (DatePickerDialog.OnDateSetListener) getActivity(),
            year, month, day);
   }
}
