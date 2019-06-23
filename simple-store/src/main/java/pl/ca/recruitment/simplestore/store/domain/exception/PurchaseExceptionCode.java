package pl.ca.recruitment.simplestore.store.domain.exception;

public enum PurchaseExceptionCode {
    GENERAL_ERROR("Unexpected purchase error"),
    CUSTOMER_NOT_FOUND("Customer was not found in database"),
    NOT_ALL_ITEMS_FOUND("Could not find all needed items"),
    NOT_ALL_ITEMS_AVAILABLE("Item were found, but some of them were not available to buy"),
    NOT_ENOUGH_MONEY("Customer does not have enough money for purchase"),
    NOT_ENOUGH_ITEMS("Customer wanted to buy more items than is available in store");

    private String message;

    PurchaseExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
