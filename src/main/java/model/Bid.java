package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    private Integer id;;
    private User user;
    private Lot lot;
    private Double offeredPrice;
    private Timestamp dateOffered;
}
