package se.atg.service.harrykart.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.model.Result;
import se.atg.service.harrykart.model.HarryKartType;
import se.atg.service.harrykart.util.HarryKartUtils;

@RestController
@RequestMapping("/api")
public class HarryKartController {

    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public Result playHarryKart(@RequestBody HarryKartType kart) {
    	return HarryKartUtils.calculateRankings(kart);
    }

}
