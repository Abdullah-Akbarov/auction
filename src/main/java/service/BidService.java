package service;

import model.Message;
import model.User;

public interface BidService {
    Message saveBid(int lotId, double bid, User user);
}
