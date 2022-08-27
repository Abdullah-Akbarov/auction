package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
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
    private boolean isActive;
}
