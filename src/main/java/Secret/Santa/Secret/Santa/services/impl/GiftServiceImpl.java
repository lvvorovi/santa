package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GiftServiceImpl implements IGiftService {
    @Autowired
    IGiftRepo iGiftRepo;

    @Override
    public List<Gift> getAllGifts() {
        return iGiftRepo.findAll();
    }

    @Override
    public Gift getGiftById(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);
        return optionalGift.orElseThrow(() -> new EntityNotFoundException("Gift not found with id " + giftId));
    }

    @Override
    public Gift createGift(GiftDTO giftDTO) {
        Gift gift = new Gift();
        gift.setName(giftDTO.getName());
        gift.setDescription(giftDTO.getDescription());
        gift.setLink(giftDTO.getLink());
        gift.setPrice(giftDTO.getPrice());
        gift.setGroup(giftDTO.getGroup());

        return iGiftRepo.save(gift);
    }


    @Override
    public Gift updateGift(int giftId, GiftDTO updatedGiftDTO) {
        Optional<Gift> existingGift = iGiftRepo.findById(giftId);

        if (existingGift.isPresent()) {
            Gift gift = existingGift.get();

            if (Objects.nonNull(updatedGiftDTO.getName())) {
                gift.setName(updatedGiftDTO.getName());
            }
            if (Objects.nonNull(updatedGiftDTO.getDescription())) {
                gift.setDescription(updatedGiftDTO.getDescription());
            }
            if (Objects.nonNull(updatedGiftDTO.getLink())) {
                gift.setLink(updatedGiftDTO.getLink());
            }
            if (Objects.nonNull(updatedGiftDTO.getPrice())) {
                gift.setPrice(updatedGiftDTO.getPrice());
            }
            if (Objects.nonNull(updatedGiftDTO.getGroup())) {
                gift.setGroup(updatedGiftDTO.getGroup());
            }

            return iGiftRepo.save(gift);
        }
        throw new EntityNotFoundException(" not found with id " + giftId);
    }

    @Override
    public void deleteGift(int giftId) {
        iGiftRepo.deleteById(giftId);
    }
}
