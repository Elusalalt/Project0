package org.example.Service.Models;

public class LibraryMember {
    private Integer id;
    private String name;
    private double feesOwed;

    public LibraryMember(){}

    public LibraryMember(Integer id, String name, double feesOwed) {
        this.id = id;
        this.name = name;
        this.feesOwed = feesOwed;
    }

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
    public String toString() {
        return "LibraryMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", feesOwed=" + feesOwed +
                '}';
    }
}
