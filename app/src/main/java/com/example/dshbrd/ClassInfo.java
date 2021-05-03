package com.example.dshbrd;

public class ClassInfo {
    String entry, className, classSchoolName;

    public ClassInfo() {
    }

    public ClassInfo(String entry, String className, String classSchoolName) {
        this.entry = entry;
        this.className = className;
        this.classSchoolName = classSchoolName;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSchoolName() {
        return classSchoolName;
    }

    public void setClassSchoolName(String classSchoolName) {
        this.classSchoolName = classSchoolName;
    }
}
