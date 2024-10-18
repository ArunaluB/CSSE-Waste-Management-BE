package edu.sliit.service.impl;

import edu.sliit.config.ModelMapperSingleton;
import edu.sliit.document.Bin;
import edu.sliit.dto.BinDto;
import edu.sliit.dto.GetBinDto;
import edu.sliit.repository.BinRepository;
import edu.sliit.service.BinService;
import edu.sliit.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BinService interface.
 * Provides methods to manage Bin-related operations, such as creating,
 * and retrieving Bin, while handling various exceptions related to data validation
 * and database interactions.
 *
 * This service includes methods for:
 * - Adding a Bin
 * - Fetching Bin by credentials or userid
 *
 * @see BinService
 * @see Bin
 * @see BinRepository
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BinServiceImpl implements BinService {

    private final BinRepository binRepository;
    private final ModelMapper modelMapper = ModelMapperSingleton.getInstance();  // Use the Singleton


    /**
     * Creates a new Bin by generating a unique Bin ID and saving the Bin data
     * into the repository. Returns a success message upon successful creation.
     *
     * @param binDto The Bin details provided by the client.
     * @return ResponseEntity<String> indicating success or failure.
     * @throws IllegalArgumentException If invalid data is provided.
     * @throws EntityNotFoundException If an entity is not found during the operation.
     * @throws Exception If an unexpected error occurs.
     */
    public ResponseEntity<String> createBin(BinDto binDto) {
        try {
            long binCount = binRepository.count();
            Bin binEntity = modelMapper.map(binDto, Bin.class);
            binEntity.setBinId(binCount + Constants.BIN_ID_INCREMENT);
            binRepository.save(binEntity);
            return ResponseEntity.ok(Constants.BIN_ADDED_SUCCESS + binEntity.getBinId());
        } catch (IllegalArgumentException ex) {
            log.error(Constants.INVALID_DATA_PROVIDED + ex.getMessage(), ex);
            return ResponseEntity.badRequest().body(Constants.INVALID_DATA_PROVIDED + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.ENTITY_NOT_FOUND + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_NOT_FOUND).body(Constants.ENTITY_NOT_FOUND + ex.getMessage());
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            return ResponseEntity.status(Constants.HTTP_INTERNAL_SERVER_ERROR)
                    .body(Constants.INTERNAL_SERVER_ERROR + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of Bin that match the provided userid.
     * Converts each Bin entity into a GetBinDto.
     *
     * @param userid The userid.
     * @return List<GetBinDto> A list of matching Bin.
     * @throws EntityNotFoundException If no Bin are found with the provided credentials.
     * @throws Exception If an unexpected error occurs during retrieval.
     */
    @Override
    public List<GetBinDto> getAllBinByUserId (String userid) {
        try {
            List<Bin> binList = binRepository.findAllByUserId(userid);
            return binList.stream()
                    .map(bin -> modelMapper.map(bin, GetBinDto.class))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            log.error(Constants.BIN_NOT_FOUND + userid, ex);
            throw ex;
        } catch (Exception ex) {
            log.error(Constants.INTERNAL_SERVER_ERROR + ex.getMessage(), ex);
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR, ex);
        }
    }
}

