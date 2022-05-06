package gov.iti.jets.persistance.entity.enums;

public enum ProductState {
    NEW("New"),
    OUT_OF_STOCK("Out of stock"),
    ON_SALE("On sale"),
    BEST_SELLER("Best seller"),
    ARCHIVED("Archived");

    private final String name;

    ProductState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}