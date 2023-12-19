package be.pxl.hotel.api.response;

public class ToastrErrorResponseDTO {
    private String message;

    public ToastrErrorResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
