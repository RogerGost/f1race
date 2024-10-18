package cat.uvic.teknos.f1race.services;

import cat.uvic.teknos.f1race.models.Team;
import cat.uvic.teknos.f1race.services.controllers.TeamController;
import cat.uvic.teknos.f1race.services.exeption.ResourceNotFoundExeption;
import cat.uvic.teknos.f1race.services.exeption.ServerErrorExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

public class RequestRouter {
    private static RawHttp rawHttp = new RawHttp();
    public RawHttpResponse<?> execRequest(RawHttpRequest request) throws JsonProcessingException {
        var path = request.getUri().getPath();
        var pathParts = path.split("/");
        var controllerName = pathParts[1];
        var method = request.getMethod();
        var responseJsonBody = "";

        switch (controllerName){
            case "team":
                var controller= new TeamController();

                if (method == "POST"){
                    var teamJson = request.getBody().get().toString();
                    var mapper = new ObjectMapper();
                    try{
                        var team = mapper.readValue(teamJson, Team.class);
                        controller.post(team);
                    }catch (JsonProcessingException e){
                        throw new RuntimeException(e);
                    }
                }else if (method =="GET" && pathParts.length == 2){
                    responseJsonBody = controller.get();

                }else if (method == "DELETE"){
                    var teamId = Integer.parseInt(pathParts[2]);
                    controller.delete(teamId);

                }else if (method == "PUT"){
                    var teamId = Integer.parseInt(pathParts[2]);
                    var mapper = new ObjectMapper();
                    try{
                        var teamJson = request.getBody().get().toString();
                        var team = mapper.readValue(teamJson, Team.class);
                        controller.put(teamId, team);
                    }catch (JsonProcessingException e){
                        throw new RuntimeException(e);
                    }
                }

        }

        RawHttpResponse response= null;
        try{

            //TODO: Router logic


            var json = ""; //teamController.get()
            response = rawHttp.parseResponse("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/json\r\n" +
                    "Content-Length: " + responseJsonBody.length() + "\r\n" +
                    "\r\n" +
                    responseJsonBody);
        }catch (ResourceNotFoundExeption exeption){
            response = null;
        }catch (ServerErrorExeption exeption){

        }
        return null;
    }


}
