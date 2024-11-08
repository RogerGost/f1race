package cat.uvic.teknos.f1race.clients.console.utils;

import cat.uvic.teknos.f1race.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mappers {
    private static final ObjectMapper mapper;

    static  {
        SimpleModule addressTypeMapping = new SimpleModule()
                .addAbstractTypeMapping(Team.class, cat.uvic.teknos.f1race.clients.console.dto.TeamDto.class)
                .addAbstractTypeMapping(Driver.class, cat.uvic.teknos.f1race.clients.console.dto.DriverDto.class)
                .addAbstractTypeMapping(Car.class, cat.uvic.teknos.f1race.clients.console.dto.CarDto.class)
                .addAbstractTypeMapping(Sponsor.class, cat.uvic.teknos.f1race.clients.console.dto.SponsorDto.class)
                .addAbstractTypeMapping(RaceResult.class, cat.uvic.teknos.f1race.clients.console.dto.RaceResultDto.class);

        mapper = new ObjectMapper();
        mapper
                .registerModule(new JavaTimeModule())
                .registerModule(addressTypeMapping);
    }

    public static ObjectMapper get() {
        return mapper;
    }
}
