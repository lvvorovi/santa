package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
@Validated
public class GiftController {
    @Autowired
    private IGiftService giftService;

    private GiftMapper giftMapper;
    @Autowired
    private IGiftRepo giftRepo;

    public GiftController(IGiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public ResponseEntity<List<GiftDTO>> getAllGifts() {
        List<GiftDTO> gifts = giftService.getAllGifts();
        return new ResponseEntity<>(gifts, HttpStatus.OK);
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<GiftDTO> getGiftById(
            @Valid
            @Min(value = 1, message = "ID must be a non-negative integer and greater than 0")
            @PathVariable int giftId) {
        GiftDTO giftDTO = giftService.getGiftById(giftId);
        return ResponseEntity.ok(giftDTO);
    }

    @PostMapping
    public ResponseEntity<GiftDTO> createGift(@RequestBody @Valid GiftDTO giftDTO) {
        GiftDTO createdGiftDTO = giftService.createGift(giftDTO);
        return ResponseEntity.ok(createdGiftDTO);
    }

    @PutMapping("/{giftId}")
    public ResponseEntity<GiftDTO> updateGift(@RequestBody GiftDTO giftDTO) {

        GiftDTO updatedGift = giftService.updateGift(giftDTO);
        return new ResponseEntity<>(updatedGift, HttpStatus.OK);
    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<String> deleteGift(@PathVariable int giftId) {
        if (Boolean.TRUE.equals(giftService.deleteGift(giftId))) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("Delete failed");
    }

}
