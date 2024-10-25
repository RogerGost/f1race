package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.services.controllers.Controller;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.exeption.ServerErrorExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import java.io.IOException;
import java.util.Map;

public class RequestRouterImplementation implements RequestRouter {
    private static RawHttp rawHttp = new RawHttp();
    private final Map<String, Controller> controllers;

    public RequestRouterImplementation(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    @Override
    public RawHttpResponse<?> execRequest(RawHttpRequest request) throws JsonProcessingException {
        var path = request.getUri().getPath();
        var pathParts = path.split("/");

        if (pathParts.length < 3) {
            return createJsonErrorResponse(404, "Controller not found.");
        }

        var controllerName = pathParts[2];
        var method = request.getMethod();
        var responseJsonBody = "";
        RawHttpResponse response = null;

        try {
            var controller = controllers.get(controllerName);
            if (controller == null) {
                throw new ResourceNotFoundExeption("Controller not found: " + controllerName);
            }

            if ("POST".equals(method)) {
                var bodyBytes = request.getBody().get().decodeBody();
                var bodyString = new String(bodyBytes);
                System.out.println("Received JSON body: " + bodyString);

                controller.post(bodyString);
            } else if ("GET".equals(method)) {
                if (pathParts.length == 4) {
                    var personId = Integer.parseInt(pathParts[3]);
                    responseJsonBody = controller.get(personId);
                } else {
                    responseJsonBody = controller.get();
                }

            } else if ("DELETE".equals(method)) {
                var personId = Integer.parseInt(pathParts[3]);
                controller.delete(personId);

            } else if ("PUT".equals(method)) {
                var personId = Integer.parseInt(pathParts[3]);
                var bodyBytes = request.getBody().get().decodeBody();
                var bodyString = new String(bodyBytes);
                controller.put(personId, bodyString);
            }

            response = rawHttp.parseResponse("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + responseJsonBody.length() + "\r\n" +
                    "\r\n" +
                    responseJsonBody);

        } catch (ResourceNotFoundExeption e) {
            response = createJsonErrorResponse(404, e.getMessage());
        } catch (RuntimeException e) {
            response = createJsonErrorResponse(500, "Internal Server Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    private RawHttpResponse<?> createJsonErrorResponse(int statusCode, String message) {
        String jsonResponse = "{\"error\": \"" + message + "\"}";
        return rawHttp.parseResponse("HTTP/1.1 " + statusCode + " " + (statusCode == 404 ? "Not Found" : "Internal Server Error") + "\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + jsonResponse.length() + "\r\n" +
                "\r\n" +
                jsonResponse);
    }


}