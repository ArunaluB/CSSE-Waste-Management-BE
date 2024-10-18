package edu.sliit.controller;

import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.service.BinService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller to handle Bin-related operations.
 * It provides endpoints to add new bins and retrieve bin details for a user.
 */

@RestController
@Slf4j
@RequestMapping(Constants.BIN_BASE_URL)
@RequiredArgsConstructor
public class BinController {

    private final BinService binService;

    /**
     * Endpoint to add a new bin.
     *
     * @param binDto Data Transfer Object containing the bin details to be added.
     * @return ResponseEntity with a success message or error details.
     */
    @PostMapping(Constants.BIN_ADD_URL)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addBin(@RequestBody BinDto binDto) {
        log.info(Constants.LOG_CREATING_BIN, binDto);
        return binService.createBin(binDto);
    }

    /**
     * Endpoint to get all bin details for a specific user.
     *
     * @param userid The ID of the user whose bin details are requested.
     * @return List of GetBinDto objects containing bin details for the specified user.
     */
    @GetMapping(Constants.BIN_GET_DETAILS_URL)
    public List<GetBinDto> getAllDetails(@RequestParam String userid) {
        log.info(Constants.LOG_FETCHING_BIN_DETAILS, userid);
        return binService.getAllBinByUserId(userid);
    }
}
