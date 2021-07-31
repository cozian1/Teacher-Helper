package com.spectrum.task3.ModelClasses;

public class Mylist {
   String title;
   String description;
   String date;

   public Mylist() {
   }

   public Mylist(String title, String description, String date) {
      this.title = title;
      this.description = description;
      this.date = date;
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
}