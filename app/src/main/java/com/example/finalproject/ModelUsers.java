package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelUsers implements Parcelable{
    String usersName,usersEmail,usersAge,usersPhoneNumber,usersAddress,usersBirthdate,userId,userBalance;

    public ModelUsers(){

    }

    public ModelUsers(String usersName, String usersEmail, String usersAge, String usersPhoneNumber, String usersAddress, String usersBirthdate, String userId) {
        this.usersName = usersName;
        this.usersEmail = usersEmail;
        this.usersAge = usersAge;
        this.usersPhoneNumber = usersPhoneNumber;
        this.usersAddress = usersAddress;
        this.usersBirthdate = usersBirthdate;
        this.userId = userId;
    }

    protected ModelUsers(Parcel in) {
        usersName = in.readString();
        usersEmail = in.readString();
        usersAge = in.readString();
        usersPhoneNumber = in.readString();
        usersAddress = in.readString();
        usersBirthdate = in.readString();
        userId = in.readString();
    }

    public static final Creator<ModelUsers> CREATOR = new Creator<ModelUsers>() {
        @Override
        public ModelUsers createFromParcel(Parcel in) {
            return new ModelUsers(in);
        }

        @Override
        public ModelUsers[] newArray(int size) {
            return new ModelUsers[size];
        }
    };

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersAge() {
        return usersAge;
    }

    public void setUsersAge(String usersAge) {
        this.usersAge = usersAge;
    }

    public String getUsersPhoneNumber() {
        return usersPhoneNumber;
    }

    public void setUsersPhoneNumber(String usersPhoneNumber) {
        this.usersPhoneNumber = usersPhoneNumber;
    }

    public String getUsersAddress() {
        return usersAddress;
    }

    public void setUsersAddress(String usersAddress) {
        this.usersAddress = usersAddress;
    }

    public String getUsersBirthdate() {
        return usersBirthdate;
    }

    public void setUsersBirthdate(String usersBirthdate) {
        this.usersBirthdate = usersBirthdate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(usersName);
        parcel.writeString(usersEmail);
        parcel.writeString(usersAge);
        parcel.writeString(usersPhoneNumber);
        parcel.writeString(usersAddress);
        parcel.writeString(usersBirthdate);
        parcel.writeString(userId);
    }
}
