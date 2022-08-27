package service;

import model.Message;
import model.User;

public interface LotService {

    Message addLot(String model, String description, Double initialPrice, User user);
    Message closeLot(Integer id);
}
