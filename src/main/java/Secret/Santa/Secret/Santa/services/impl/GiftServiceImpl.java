package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import Secret.Santa.Secret.Santa.validationUnits.GroupUtils;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements IGiftService {
    private static final Logger logger = LoggerFactory.getLogger(GiftServiceImpl.class);
    private final GiftMapper giftMapper;
    @Autowired
    IGiftRepo iGiftRepo;

    private final GroupUtils groupUtils;
    private final UserUtils userUtils;

    @Autowired
    public GiftServiceImpl(GiftMapper giftMapper, IGiftRepo iGiftRepo, GroupUtils groupUtils, UserUtils userUtils) {
        this.giftMapper = giftMapper;
        this.iGiftRepo = iGiftRepo;
        this.groupUtils = groupUtils;
        this.userUtils = userUtils;
    }

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
    public Gift createGift(Integer userId, GiftDTO giftDTO) {
        try {
            if (giftDTO.getPrice() < 0) {
                throw new SantaValidationException("Price cannot be negative", "price", "NegativeValue", String.valueOf(giftDTO.getPrice()));
            }
            Group group = groupUtils.getGroupById(giftDTO.getGroupId());
            if (giftDTO.getPrice() > group.getBudget()) {
                throw new SantaValidationException("Price cannot be bigger than group budget", "price", "BiggerThanBudget", String.valueOf(giftDTO.getPrice()));
            }

            giftDTO.setCreatedBy(userId);
            Gift gift = giftMapper.toGift(giftDTO);
            return iGiftRepo.save(gift);
        } catch (Exception e) {
            logger.error("Error creating gift", e);
            throw e;
        }
    }

    @Override
    public GiftDTO updateGift(int giftId, GiftDTO giftDTO) {

        Group group = groupUtils.getGroupById(giftDTO.getGroupId());
        if (giftDTO.getPrice() > group.getBudget()) {
            throw new SantaValidationException("Price cannot be bigger than group budget", "price", "BiggerThanBudget", String.valueOf(giftDTO.getPrice()));
        }

        if (giftDTO == null) {
            throw new IllegalArgumentException("GiftDTO cannot be null");
        }
        Optional<Gift> existingGift = iGiftRepo.findById(giftId);
        if (existingGift.isPresent()) {
            Gift gift = existingGift.get();
            gift = giftMapper.toGift(giftDTO, gift);
            iGiftRepo.save(gift);
            return giftMapper.toGiftDTO(gift);
        }
        throw new EntityNotFoundException("User not found with id " + giftId);
    }


    @Override
    public boolean deleteGift(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);
        if (optionalGift.isPresent()) {
            try {
                iGiftRepo.deleteById(giftId);
                return true;
            } catch (Exception exception) {
                logger.error("Exception occurred while deleting gift with ID: {}", giftId, exception);
                return false;
            }
        } else {
            logger.error("Attempted to delete a gift that does not exist with ID: {}", giftId);
            throw new EntityNotFoundException("Gift not found with id " + giftId);
        }
    }

    @Override
    public List<Gift> getGiftsCreatedBy(int userId) {
        User user = userUtils.getUserById(userId);
        return iGiftRepo.findByCreatedBy(user);
    }
}
