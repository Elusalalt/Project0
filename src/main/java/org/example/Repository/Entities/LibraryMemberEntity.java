package org.example.Repository.Entities;

import org.example.Repository.DAO.LibraryMemberDAO;

public class LibraryMemberEntity {
    private Integer id;
    private String name;
    private double feesOwed;

    public LibraryMemberEntity(String name, Integer id) {
        this.name = name;
        this.feesOwed = 0;
        this.id = id;
    }
    public LibraryMemberEntity(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFeesOwed() {
        return feesOwed;
    }

    public void setFeesOwed(double feesOwed) {
        this.feesOwed = feesOwed;
    }

    @Override
    public String toString(){
        return "Member{" +
                "ID=" + id +
                ", Name='" + name +
                ", Fees owed on books that have already been returned='$" + feesOwed +
                '}';
    }
}


