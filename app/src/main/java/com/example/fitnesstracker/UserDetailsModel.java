package com.example.fitnesstracker;

public class UserDetailsModel {
    int id, age;
    double height, weight, bmi;
    String Name,Remark;

    public UserDetailsModel(int id, int age, double height, double weight, double bmi, String name, String Remark) {

        this.id = id;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.Name = name;
        this.Remark = Remark;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
