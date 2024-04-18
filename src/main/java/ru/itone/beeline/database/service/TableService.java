package ru.itone.beeline.database.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.entity.UserWebVisit;

public class TableService {

    private static final Logger log = LoggerFactory.getLogger(TableService.class);
    private final DatabaseService dbService;

    public TableService(DatabaseService databaseService) {
        this.dbService = databaseService;
    }

    public int insertRandomRows(Integer count) throws SQLException {
        PreparedStatement stmt = dbService.getBatchInsertStatement();
        int affectedRows = 0;
        try {
            for(int i=0; i<count; i++) {
                UserWebVisit row = UserWebVisit.random();
                stmt.setString(1, row.getLogin());
                stmt.setString(2, row.getLocation());
                stmt.setTimestamp(3, new Timestamp(row.getDateTime().getMillis()));
                stmt.addBatch();
                affectedRows++;
                if(i%555==0) stmt.executeBatch();
            }
            stmt.executeBatch();
            dbService.getConnection().commit();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            try {
                stmt.cancel();
                dbService.getConnection().rollback();
            } catch (SQLException e) {
                log.error(ex.getMessage(), ex);
            }
            throw ex;
        }
        return affectedRows;
    }

    public int deleteRows(DateTime fromDateTime, DateTime toDateTime, Integer count) throws SQLException {
        PreparedStatement stmt = dbService.getDeleteStatement();
        int affectedRows = 0;
        try {
            boolean proceed = true;
            while(proceed) {
                stmt.setTimestamp(1, new Timestamp(fromDateTime.getMillis()));
                stmt.setTimestamp(2, new Timestamp(toDateTime.getMillis()));
                stmt.setInt(3, count);
                int rows = stmt.executeUpdate();
                affectedRows += rows;
                proceed = rows > 0;
            }
            dbService.getConnection().commit();
        } catch (SQLException ex) {
            log.error(ex.getMessage(), ex);
            try {
                stmt.cancel();
                dbService.getConnection().rollback();
            } catch (SQLException e) {
                log.error(ex.getMessage(), ex);
            }
            throw ex;
        }
        return affectedRows;
    }
}
