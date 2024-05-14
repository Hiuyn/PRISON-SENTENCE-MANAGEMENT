package com.example.psmsystem.model.prisoner;

import javafx.collections.ObservableList;

import java.util.List;

public interface IPrisonerDao<T> {
    List<T> getAllPrisoner();
    List<Prisoner>getPrisonerInItem();

    ObservableList<T> getPrisonerName();
}
