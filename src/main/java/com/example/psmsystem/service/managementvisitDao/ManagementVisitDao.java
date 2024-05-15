package com.example.psmsystem.service.managementvisitDao;

import com.example.psmsystem.database.DbConnection;
import com.example.psmsystem.model.managementvisit.IManagementVisitDao;
import com.example.psmsystem.model.managementvisit.ManagementVisit;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagementVisitDao implements IManagementVisitDao<ManagementVisit> {
    private static final String INSERT_QUERY = "INSERT INTO visitation (prisoner_code, visitor_name, national_identification_number, relationship, visit_date, notes) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MANAGEMENTVISIT_QUERY = "UPDATE visitation SET prisoner_code = ?, visitor_name = ?, national_identification_number = ?, relationship = ?, visit_date = ?, notes = ? WHERE Visitation_id = ?";
    private static final String DELETE_MANAGEMENTVISIT_QUERY = "DELETE FROM visitation WHERE Visitation_id = ?";
    private static final String SELECT_BY_MANAGEMENTVISIT_QUERY = "SELECT * FROM visitation";
    private static final String SELECT_BY_CODE_DATE_MANAGEMENTVISIT_QUERY = "SELECT * FROM visitation WHERE prisoner_code = ? AND visit_date = ?";
    @Override
    public void addManagementVisit(ManagementVisit managementVisit) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY);
            ps.setString(1,managementVisit.getPrisonerCode());
            ps.setString(2,managementVisit.getVisitorName());
            ps.setString(3,managementVisit.getNationalIdentificationNumber());
            ps.setString(4,managementVisit.getRelationship());
            ps.setString(5,managementVisit.getVisitDate());
            ps.setString(6,managementVisit.getNotes());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ManagementVisit> getManagementVisits() {
        List<ManagementVisit> ManagementVisitList = new ArrayList<>();
        try {

            Connection conn = DbConnection.getDatabaseConnection().getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_BY_MANAGEMENTVISIT_QUERY);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ManagementVisit managementVisit = new ManagementVisit();
                managementVisit.setPrisonerCode(rs.getString("prisoner_code"));
                managementVisit.setVisitorName(rs.getString("visitor_name"));
                managementVisit.setNationalIdentificationNumber(rs.getString("national_identification_number"));
                managementVisit.setRelationship(rs.getString("relationship"));
                managementVisit.setVisitDate(rs.getString("visit_date"));
                managementVisit.setNotes(rs.getString("notes"));
                ManagementVisitList.add(managementVisit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ManagementVisitList;
    }

    @Override
    public void updateManagementVisit(ManagementVisit managementVisit, int id) {
        try(Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(UPDATE_MANAGEMENTVISIT_QUERY)) {
            ps.setString(1, managementVisit.getPrisonerCode());
            ps.setString(2, managementVisit.getVisitorName());
            ps.setString(3, managementVisit.getNationalIdentificationNumber());
            ps.setString(4, managementVisit.getRelationship());
            ps.setString(5, managementVisit.getVisitDate());
            ps.setString(6, managementVisit.getNotes());
            ps.setInt(7, id);
            ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteManagementVisit(int id) {
        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(DELETE_MANAGEMENTVISIT_QUERY)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getVisitationId(String prisonerCode, String visitDate) {
        int visitationId = -1;

        try (Connection connection = DbConnection.getDatabaseConnection().getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE_DATE_MANAGEMENTVISIT_QUERY)) {
                ps.setString(1, prisonerCode);
                ps.setString(2, visitDate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        visitationId = rs.getInt("Visitation_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visitationId;
    }
}
