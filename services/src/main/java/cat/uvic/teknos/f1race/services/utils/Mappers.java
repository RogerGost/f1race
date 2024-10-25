package cat.uvic.teknos.f1race.services.utils;

import cat.uvic.teknos.f1race.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mappers {
    private static final ObjectMapper mapper;

    static  {
        SimpleModule addressTypeMapping = new SimpleModule()
                .addAbstractTypeMapping(Team.class, cat.uvic.teknos.f1race.domain.jbdc.models.Team.class)
                .addAbstractTypeMapping(Driver.class,  cat.uvic.teknos.f1race.domain.jbdc.models.Driver.class)
                .addAbstractTypeMapping(Car.class,  cat.uvic.teknos.f1race.domain.jbdc.models.Car.class)
                .addAbstractTypeMapping(Sponsor.class,  cat.uvic.teknos.f1race.domain.jbdc.models.Sponsor.class)
                .addAbstractTypeMapping(RaceResult.class,  cat.uvic.teknos.f1race.domain.jbdc.models.RaceResult.class);

        mapper = new ObjectMapper();
        mapper
                .registerModule(new JavaTimeModule())
                .registerModule(addressTypeMapping);
    }

    public static ObjectMapper get() {
        return mapper;
    }
}
