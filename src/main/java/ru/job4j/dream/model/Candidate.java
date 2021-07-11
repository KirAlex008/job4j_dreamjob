package ru.job4j.dream.model;

public class Candidate {
    private int id;
    private String name;
    private int city_id;
    private String photo;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate(int id, String name, Integer city_id) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
    }

    public Candidate(int id, String name, Integer city_id, String photo) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city_id=" + city_id +
                ", photo='" + photo + '\'' +
                '}';
    }
}
