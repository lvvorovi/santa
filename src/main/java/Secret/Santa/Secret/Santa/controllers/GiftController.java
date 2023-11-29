package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.DTO.UserDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/gifts")
public class GiftController {
    @Autowired
    private IGiftService giftService;

    public GiftController(IGiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public ResponseEntity<List<Gift>> getAllGifts() {
        List<Gift> gifts = giftService.getAllGifts();
        return new ResponseEntity<>(gifts, HttpStatus.OK);
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<Gift> getGiftById(@Valid @PathVariable int giftId) {
        Gift gift = giftService.getGiftById(giftId);
        return ResponseEntity.ok(gift);
    }

    @PostMapping
    public ResponseEntity<Gift> createGift(@RequestBody @Valid GiftDTO giftDTO) {
        Gift createdGift = giftService.createGift(giftDTO);
        return ResponseEntity.ok(createdGift);
    }


    @PutMapping("/{giftId}")
    public ResponseEntity<Gift> updateGift(@Valid @PathVariable int giftId, @RequestBody GiftDTO updatedGiftDTO) {
        Gift updated = giftService.updateGift(giftId, updatedGiftDTO);
        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<Void> deleteGift(@PathVariable int giftId) {
        giftService.deleteGift(giftId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
