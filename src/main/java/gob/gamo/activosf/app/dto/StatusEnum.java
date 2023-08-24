package gob.gamo.activosf.app.dto;

public enum StatusEnum {
    ACTIVE("A"),
    INACTIVE("I");

    private String status;

    private StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
