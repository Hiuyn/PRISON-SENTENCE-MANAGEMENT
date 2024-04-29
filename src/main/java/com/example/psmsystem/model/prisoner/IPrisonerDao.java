package com.example.psmsystem.model.prisoner;

import java.util.List;

public interface IPrisonerDao<T> {
    List<T> getAllPrisoner();
}
