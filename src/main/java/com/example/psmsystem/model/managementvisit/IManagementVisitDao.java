package com.example.psmsystem.model.managementvisit;

import java.util.List;

public interface IManagementVisitDao<T> {
    void addManagementVisit(T t);
    List<T> getManagementVisits();
    void updateManagementVisit(T t, int id);
    void deleteManagementVisit(int id);
    int getVisitationId(String prisonerCode, String visitDate);
}
