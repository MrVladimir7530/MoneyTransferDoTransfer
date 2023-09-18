package org.example.model;

import java.util.Objects;

public class PersonInfo {
    private long id;
    private long personId;
    private String phone;

    public PersonInfo() {
    }
    public PersonInfo(long id, long personId, String phone) {
        this.id = id;
        this.personId = personId;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInfo that = (PersonInfo) o;
        return id == that.id && personId == that.personId && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, phone);
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id=" + id +
                ", personId=" + personId +
                ", phone='" + phone + '\'' +
                '}';
    }
}
