package dao.impl;

import dao.LotDao;
import dao.PostgresConnection;
import model.Lot;
import model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class LotDaoImpl implements LotDao {
    private static Connection con = PostgresConnection.getInstance();
    private static LotDao lotDao;
    public static LotDao getLotDao(){
        if (lotDao==null){
            lotDao = new LotDaoImpl();
        }
        return lotDao;
    }
    @Override
    public boolean save(Lot lot) {
        try {
            PreparedStatement statement = con.prepareStatement("insert into lot(model, description, initial_price, user_id) values(?,?,?,?)");
            statement.setString(1, lot.getModel());
            statement.setString(2, lot.getDescription());
            statement.setDouble(3, lot.getInitialPrice());
            statement.setInt(4, lot.getUser().getId());
            int i = statement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Lot> getById(Integer id) {
        try {
            User user = new User();
            PreparedStatement statement = con.prepareStatement("select * from lot where id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                String model = resultSet.getString(2);
                String description = resultSet.getString(3);
                double initialPrice = resultSet.getDouble(4);
                Timestamp dateStarted = resultSet.getTimestamp(5);
                int userId = resultSet.getInt(6);
                boolean isActive = resultSet.getBoolean(7);
                user.setId(userId);
                return Optional.of(new Lot(id,model, description, initialPrice,dateStarted, user, isActive));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    @Override
    public boolean checkBid(Integer lotId, double bid) {
        Optional<Lot> byId = lotDao.getById(lotId);
        return byId.get().getInitialPrice() <= bid;
    }

    @Override
    public boolean closeLot(Integer id) {
        try {
            PreparedStatement statement = con.prepareStatement("update lot set is_active = false where id = " + id);
            int i = statement.executeUpdate();
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
