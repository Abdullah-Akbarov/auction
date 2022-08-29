package service.impl;

import dao.BidDao;
import dao.LotDao;
import dao.impl.BidDaoImpl;
import dao.impl.LotDaoImpl;
import model.Bid;
import model.Lot;
import model.Message;
import model.User;
import service.LotService;

import java.util.Optional;

public class LotServiceImpl implements LotService {
    private static LotService lotService = new LotServiceImpl();
    private final LotDao lotDao = LotDaoImpl.getLotDao();
    private final BidDao bidDao = BidDaoImpl.getBidDao();

    public static LotService getLotService() {
        if (lotService == null) {
            lotService = new LotServiceImpl();
        }
        return lotService;
    }

    @Override
    public Message addLot(String model, String description, Double initialPrice, User user) {
        Message message;
        Lot lot = new Lot();
        lot.setModel(model);
        lot.setInitialPrice(initialPrice);
        lot.setUser(user);
        lot.setDescription(description);
        if (lotDao.save(lot)) {
            message = new Message(202, "saved", model);
        } else {
            message = new Message(403, "data has not been saved", null);
        }
        return message;
    }

    @Override
    public Message closeLot(Integer id) {
        Message message;
        boolean lot = lotDao.closeLot(id);
        Optional<Bid> maxBid = bidDao.getMaxBid(id);
        if (maxBid.isPresent()){
            if (lot) {
                message = new Message(201, "lot closed", maxBid.get());
            } else {
                message = new Message(403, "could not close", null);
            }
        } else {
            message = new Message(403, "You can't close the lot without any bids", null);
        }
        return message;
    }
}
