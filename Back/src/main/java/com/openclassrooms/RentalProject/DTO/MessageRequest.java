package com.openclassrooms.RentalProject.DTO;

import lombok.Data;

@Data
public class MessageRequest {
    int rental_id;
    int user_id;
    String message;
}
