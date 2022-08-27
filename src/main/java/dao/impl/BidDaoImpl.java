package dao.impl;

import dao.BidDao;
import dao.LotDao;
import dao.PostgresConnection;
import model.Bid;
import model.Lot;
import model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BidDaoImpl implements BidDao {
    private static Connection con = PostgresConnection.getInstance();
    private static BidDao bidDao;
    public static BidDao getBidDao(){
        if (bidDao==null){
            bidDao = new BidDaoImpl();
        }
        return bidDao;
    }

    @Override
    public boolean save(Bid bid) {
        try {
            PreparedStatement statement = con.prepareStatement("insert into bid(user_id, lot_id, offered_price) values (?, ?, ?)");
            statement.setInt(1, bid.getUser().getId());
            statement.setInt(2, bid.getLot().getId());
            statement.setDouble(3, bid.getOfferedPrice());
            int i = statement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Bid> getMaxBid(Integer id) {
        try {
            User user = new User();
            Lot lot = new Lot();
            PreparedStatement statement = con.prepareStatement("select * from bid where lot_id = ? order by offered_price desc limit 1");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int id1 = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                double offer = resultSet.getDouble(4);
                Timestamp timestamp = resultSet.getTimestamp(5);
                user.setId(userId);
                lot.setId(id);
                return Optional.of(new Bid(id1, user, lot,offer, timestamp));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
