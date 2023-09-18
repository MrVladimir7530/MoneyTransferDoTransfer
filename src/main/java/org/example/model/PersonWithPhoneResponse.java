package org.example.model;

public class PersonWithPhoneResponse {
    private PersonInfo fromPersonId;
    private PersonInfo toPersonId;

    public PersonWithPhoneResponse() {

    }
    public PersonWithPhoneResponse(PersonInfo fromPersonId, PersonInfo toPersonId) {
        this.fromPersonId = fromPersonId;
        this.toPersonId = toPersonId;
    }

    public PersonInfo getFromPersonId() {
        return fromPersonId;
    }

    public PersonInfo getToPersonId() {
        return toPersonId;
    }
}
