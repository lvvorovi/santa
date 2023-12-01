package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Gift>> getAllGifts() {
        List<Gift> gifts = giftService.getAllGifts();
        return new ResponseEntity<>(gifts, HttpStatus.OK);
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<Gift> getGiftById(@PathVariable int giftId) {
        Gift gift = giftService.getGiftById(giftId);
        return ResponseEntity.ok(gift);
    }

    @PostMapping
    public ResponseEntity<Gift> createGift(@RequestBody @Valid GiftDTO giftDTO) {
        Gift createdGift = giftService.createGift(giftDTO);
        return ResponseEntity.ok(createdGift);
    }

    @PutMapping("/{giftId}")
    public ResponseEntity<GiftDTO> updateGift(@PathVariable int giftId, @RequestBody GiftDTO giftDTO) {

        GiftDTO updatedGift = giftService.updateGift(giftId, giftDTO);
        return new ResponseEntity<>(updatedGift, HttpStatus.OK);
    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<Void> deleteGift(@PathVariable int giftId) {
        giftService.deleteGift(giftId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
