package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
@Validated
@RequiredArgsConstructor
public class GiftController {
    private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
    @Autowired
    private final IGiftService giftService;

    private final UserUtils userUtils;

    @GetMapping
    public ResponseEntity<List<GiftDTO>> getAllGifts() {
        try {
            List<GiftDTO> gifts = giftService.getAllGifts();
            return new ResponseEntity<>(gifts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving all gifts", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<GiftDTO> getGiftById(@Valid
                                               @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
                                               @PathVariable int giftId) {
        try {
            GiftDTO giftDTO = giftService.getGiftById(giftId);
            return ResponseEntity.ok(giftDTO);
        } catch (Exception e) {
            logger.error("Error retrieving gift with ID: {}", giftId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<GiftDTO> createGift(@RequestBody @Valid GiftDTO giftDTO) {
        try {
            GiftDTO createdGiftDTO = giftService.createGift(giftDTO);
            return ResponseEntity.ok(createdGiftDTO);
        } catch (Exception e) {
            logger.error("Error creating gift", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<GiftDTO> updateGift(@RequestBody GiftDTO giftDTO) {
        try {
            GiftDTO updatedGiftDTO = giftService.updateGift(giftDTO);
            return new ResponseEntity<>(updatedGiftDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating gift with ID: {}", giftDTO.getGiftId(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<Void> deleteGift(@PathVariable int giftId) {
        try {
            giftService.deleteGift(giftId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting gift with ID: {}", giftId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/createdBy/{userId}")
    public ResponseEntity<List<Gift>> getGiftsCreatedByUser(@PathVariable int userId) {

        try {

            List<Gift> userGifts = giftService.getGiftsCreatedBy(userId);
            return ResponseEntity.ok(userGifts);
          
        } catch (Exception e) {
            logger.error("Error retrieving gifts created by user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
