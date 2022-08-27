package service.impl;

import dao.BidDao;
import dao.LotDao;
import dao.impl.BidDaoImpl;
import dao.impl.LotDaoImpl;
import model.Bid;
import model.Lot;
import model.Message;
import model.User;
import service.BidService;

public class BidServiceImpl implements BidService {
    private static BidService bidService;
    private final BidDao bidDao = BidDaoImpl.getBidDao();
    private final LotDao lotDao = LotDaoImpl.getLotDao();

    public static BidService getBidService() {
        if (bidService == null) {
            bidService = new BidServiceImpl();
        }
        return bidService;
    }

    @Override
    public Message saveBid(int lotId, double bid, User user) {
        Bid bid1 = new Bid();
        Lot lot = new Lot();
        lot.setId(lotId);
        bid1.setUser(user);
        bid1.setLot(lot);
        bid1.setOfferedPrice(bid);
        Message message;

        if (lotDao.checkBid(lotId, bid)) {
            if (bidDao.save(bid1)) {
                message = new Message(202, "Accepted", bid);
            } else {
                message = new Message(406, "Not acceptable", bid);
            }
        } else {
            message = new Message(406, "Not acceptable", bid);
        }
        return message;
    }

}
