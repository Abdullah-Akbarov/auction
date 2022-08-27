package dao;

import model.Lot;

import java.util.List;
import java.util.Optional;

public interface LotDao{
    boolean save(Lot lot);
    Optional<Lot> getById(Integer id);
    boolean checkBid(Integer lotId, double bid);
    boolean closeLot(Integer id);
}
