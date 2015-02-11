package org.reactome.server.models2pathways.service.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.reactome.server.models2pathways.service.hepler.JSONObjFactory;
import org.reactome.server.models2pathways.service.model.BioModelsIdentifier;
import org.reactome.server.models2pathways.service.model.PathwayIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Maximilian Koch (mkoch@ebi.ac.uk).
 */

@Controller
@Api(value = "models2pathways", description = "Needs to be changed")
@RequestMapping(value = "/models2pathways")
public class RequestController {

    @ApiResponses({@ApiResponse(code = 404, message = "Needs to be changed")})
    @RequestMapping(value = "/pathway/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public BioModelsIdentifier getBioModelsByPathway(
            @ApiParam(name = "id", required = true, value = "Needs to be changed")
            @PathVariable String id
    ) {
        return JSONObjFactory.getBioModelsIdentifier(id);
    }

    @ApiResponses({@ApiResponse(code = 404, message = "Needs to be changed")})
    @RequestMapping(value = "/biomodel/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public PathwayIdentifier getPathwaysByBioModel(
            @ApiParam(name = "id", required = true, value = "Needs to be changed")
            @PathVariable String id
    ) {
        return JSONObjFactory.getPathwayIdentifier(id);
    }
}
