package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
    private Integer id;
    private String model;
    private String description;
    private Double initialPrice;
    private Timestamp dateStarted;
    private User user;
    private boolean isActive = true;

    public Lot(Integer id, String model, String description, Double initialPrice, Timestamp dateStarted) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.initialPrice = initialPrice;
        this.dateStarted = dateStarted;
    }
}
