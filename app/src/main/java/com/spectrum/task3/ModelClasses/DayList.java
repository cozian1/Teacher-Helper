package com.spectrum.task3.ModelClasses;


public class DayList {
   String title;
   String state;

   public DayList() {
   }

   public DayList(String title, String state) {
      this.title = title;
      this.state = state;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }
}