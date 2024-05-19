package com.example.psmsystem.model.sentence;

import java.util.List;
import java.util.Map;

public interface ISentenceDao<T> {
    void addSentence(T t);
    List<T> getSentence();
    void updateSentence(T t, int id);
    void deleteSentence(int id);
    int getSentenceId(String prisonerCode);
    Map<String, Integer> countPrisonersBySentenceType();
}
