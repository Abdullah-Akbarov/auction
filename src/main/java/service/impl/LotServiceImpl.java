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
        Optional<Bid> maxBid = bidDao.getMaxBid(id);
        if (!maxBid.isPresent()) {
            boolean lot = lotDao.closeLot(id);
            if (lot) {
                message = new Message(201, "lot closed successfully", maxBid.get());
            } else {
                message = new Message(403, "could not complete", null);
            }
        } else {
            message = new Message(403, "you can't close the lot, there is not enough bidders", null);
        }
        return message;
    }
}
