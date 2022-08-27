package dao;

import model.Bid;

import java.util.List;
import java.util.Optional;

public interface BidDao{
    boolean save(Bid bid);
    Optional<Bid> getMaxBid(Integer id);
}
