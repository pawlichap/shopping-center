package pl.ca.recruitment.simplestore.store.domain.exception;

public class PurchaseItemsException extends RuntimeException {
    private PurchaseExceptionCode code;

    public PurchaseItemsException(PurchaseExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public PurchaseExceptionCode getCode() {
        return code;
    }

    public static PurchaseItemsException fromOther(Throwable ex) {
        if (ex instanceof PurchaseItemsException)
            return (PurchaseItemsException) ex;
        return new PurchaseItemsException(PurchaseExceptionCode.GENERAL_ERROR);
    }
}
