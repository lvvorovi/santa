package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
public class GiftController {
    private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
    @Autowired
    private IGiftService giftService;

    @Autowired
    private IGiftRepo giftRepo;

    public GiftController(IGiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public ResponseEntity<List<Gift>> getAllGifts() {
        try {
            List<Gift> gifts = giftService.getAllGifts();
            return new ResponseEntity<>(gifts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving all gifts", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<Gift> getGiftById(@Valid @PathVariable int giftId) {
        try {
            Gift gift = giftService.getGiftById(giftId);
            return ResponseEntity.ok(gift);
        } catch (Exception e) {
            logger.error("Error retrieving gift with ID: {}", giftId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Gift> createGift(@RequestBody @Valid GiftDTO giftDTO) {
        try {
            Gift createdGift = giftService.createGift(giftDTO);
            return ResponseEntity.ok(createdGift);
        } catch (Exception e) {
            logger.error("Error creating gift", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping("/{giftId}")
    public ResponseEntity<Gift> updateGift(@Valid @PathVariable int giftId, @RequestBody GiftDTO updatedGiftDTO) {
        try {
            Gift updatedGift = giftService.updateGift(giftId, updatedGiftDTO);
            return ResponseEntity.ok(updatedGift);
        } catch (Exception e) {
            logger.error("Error updating gift with ID: {}", giftId, e);
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

}
