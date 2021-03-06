package com.spectrum.task3.ModelClasses;

public class ReminderList {
   String title;
   String description;
   String date;
   String key;

   public ReminderList() {
   }

   public ReminderList(String title, String description, String date, String key) {
      this.title = title;
      this.description = description;
      this.date = date;
      this.key = key;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }
}
