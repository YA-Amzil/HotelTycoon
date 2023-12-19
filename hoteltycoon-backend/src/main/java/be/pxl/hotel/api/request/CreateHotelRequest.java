package be.pxl.hotel.api.request;

import jakarta.validation.constraints.NotBlank;

public record CreateHotelRequest(@NotBlank(message = "Name not be blank!") String name, long buildingplotId) {
    // TODO validation
}
