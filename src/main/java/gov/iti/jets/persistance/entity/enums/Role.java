package gov.iti.jets.persistance.entity.enums;

public enum Role {
    CUSTOMER("customer"),
    CLERK("clerk"),
    ADMIN("admin");
    private final String name;


    Role(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
