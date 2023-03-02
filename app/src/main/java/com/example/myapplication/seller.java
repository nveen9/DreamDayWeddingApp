package com.example.myapplication;

public class seller {

    String Business_Name;
    String Email;
    String Password;
    String Mobile;
    String Business_Address;
    String Business_Link;
    String Profile_Pic;
    String User_Id;
    double Latitude;
    double Longitude;
    String Locality;
    String Address;
    String Package1,Package2,Package3,Work1,Work2,Work3,Work4,Work5;
    double Starting_Price;

    public seller() {
    }

    public seller(String Business_Name, String Email, String Password, String Mobile, String Business_Address, String Business_Link, String Profile_Pic, String User_Id, double Latitude, double Longitude, String Locality, String Address, String Package1, String Package2, String Package3, String Work1, String Work2, String Work3, String Work4, String Work5, double Starting_Price) {
        this.Business_Name = Business_Name;
        this.Email = Email;
        this.Password = Password;
        this.Mobile = Mobile;
        this.Business_Address = Business_Address;
        this.Business_Link = Business_Link;
        this.Profile_Pic = Profile_Pic;
        this.User_Id = User_Id;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Locality = Locality;
        this.Address = Address;
        this.Package1 = Package1;
        this.Package2 = Package2;
        this.Package3 = Package3;
        this.Work1 = Work1;
        this.Work2 = Work2;
        this.Work3 = Work3;
        this.Work4 = Work4;
        this.Work5 = Work5;
        this.Starting_Price = Starting_Price;
    }

    public double getStarting_Price() {
        return Starting_Price;
    }

    public void setStarting_Price(double starting_Price) {
        Starting_Price = starting_Price;
    }

    public String getWork1() {
        return Work1;
    }

    public void setWork1(String work1) {
        Work1 = work1;
    }

    public String getWork2() {
        return Work2;
    }

    public void setWork2(String work2) {
        Work2 = work2;
    }

    public String getWork3() {
        return Work3;
    }

    public void setWork3(String work3) {
        Work3 = work3;
    }

    public String getWork4() {
        return Work4;
    }

    public void setWork4(String work4) {
        Work4 = work4;
    }

    public String getWork5() {
        return Work5;
    }

    public void setWork5(String work5) {
        Work5 = work5;
    }

    public String getPackage1() {
        return Package1;
    }

    public void setPackage1(String package1) {
        Package1 = package1;
    }

    public String getPackage2() {
        return Package2;
    }

    public void setPackage2(String package2) {
        Package2 = package2;
    }

    public String getPackage3() {
        return Package3;
    }

    public void setPackage3(String package3) {
        Package3 = package3;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBusiness_Name() {
        return Business_Name;
    }

    public void setBusiness_Name(String Business_Name) {
        this.Business_Name = Business_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getBusiness_Address() {
        return Business_Address;
    }

    public void setBusiness_Address(String Business_Address) {
        this.Business_Address = Business_Address;
    }

    public String getBusiness_Link() {
        return Business_Link;
    }

    public void setBusiness_Link(String Business_Link) {
        this.Business_Link = Business_Link;
    }

    public String getProfile_Pic() {
        return Profile_Pic;
    }

    public void setProfile_Pic(String Profile_Pic) {
        this.Profile_Pic = Profile_Pic;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String User_Id) {
        this.User_Id = User_Id;
    }
    
}
