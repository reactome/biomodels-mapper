package org.reactome.server.widget.entrypoint;

import org.reactome.server.widget.database.Models2PathwayDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllBioModelsIDs(ModelMap model) {
        Models2PathwayDAO models2PathwayDAO = new Models2PathwayDAO();
        Set<String> strings = models2PathwayDAO.getAllBioModelsIDs();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("biomodelsIDs", strings);
        return modelAndView;
    }
}