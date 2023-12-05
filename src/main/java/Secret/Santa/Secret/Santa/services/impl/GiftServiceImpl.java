package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GiftServiceImpl implements IGiftService {
    private static final Logger logger = LoggerFactory.getLogger(GiftServiceImpl.class);
    @Autowired
    IGiftRepo iGiftRepo;

    @Override
    public List<Gift> getAllGifts() {
        try {
            return iGiftRepo.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving all gifts", e);
            throw e;
        }
    }

    @Override
    public Gift getGiftById(int giftId) {
        try {
            return iGiftRepo.findById(giftId)
                    .orElseThrow(() -> new RuntimeException("Gift not found with id: " + giftId));
        } catch (Exception e) {
            logger.error("Failed to retrieve gift with ID: {}", giftId, e);
            throw e;
        }
    }

    @Override
    public Gift createGift(GiftDTO giftDTO) {
        try {
            Gift gift = new Gift();
            gift.setName(giftDTO.getName());
            gift.setDescription(giftDTO.getDescription());
            gift.setLink(giftDTO.getLink());
            gift.setPrice(giftDTO.getPrice());
            gift.setGroup(giftDTO.getGroup());
            return iGiftRepo.save(gift);
        } catch (Exception e) {
            logger.error("Error creating gift", e);
            throw e;
        }
    }


    @Override
    public Gift updateGift(int giftId, GiftDTO updatedGiftDTO) {
        try {
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
            throw new RuntimeException("Gift not found with id: " + giftId);
        } catch (RuntimeException e) {
            logger.error("Error occurred while updating gift with ID: {}", giftId, e);
            throw e;
        }
    }

    @Override
    public void deleteGift(int giftId) {
        try {
            iGiftRepo.deleteById(giftId);
        } catch (Exception e) {
            logger.error("Error deleting gift with ID: {}", giftId, e);
            throw e;
        }
    }
}
