package com.spectrum.task3.ModelClasses;

public class TODO {
   String title;
   String description;
   String f;

   public TODO() {
      title="";
      description="";
      f="false";
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

   public String getF() {
      return f;
   }

   public void setF(String f) {
      this.f = f;
   }

   public TODO(String title, String description, String f) {
      this.title = title;
      this.description = description;
      this.f = f;
   }
}
