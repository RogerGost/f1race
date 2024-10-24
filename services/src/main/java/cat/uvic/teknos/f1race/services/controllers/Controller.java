package cat.uvic.teknos.f1race.services.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Controller {
    String get(int id);
    String get() throws JsonProcessingException;
    void post(String json);
    void put(int id, String json);
    void delete(int id);

}
