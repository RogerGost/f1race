package cat.uvic.teknos.f1race.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

public interface RequestRouter {
    RawHttpResponse<?> execRequest(RawHttpRequest request) throws JsonProcessingException;
}
