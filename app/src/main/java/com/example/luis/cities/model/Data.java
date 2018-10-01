package com.example.luis.cities.model;


import android.support.annotation.NonNull;

import java.util.Objects;

public class Data implements Comparable{

    private String country;
    private String _id;
    private String name;
    private Coord coord;

    public Data(String id,String name,String country){
        this._id=id;
        this.name=name;
        this.country=country;

    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public int compareTo(@NonNull Object o) {

        Data data=(Data)o;

        if(this.name.equalsIgnoreCase(data.name))
            return this.country.compareTo(data.country);
        else
            return this.name.compareTo(data.name);

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        Boolean b= false;
        if(_id.compareTo(data._id)==0) b=true;
        return b;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
