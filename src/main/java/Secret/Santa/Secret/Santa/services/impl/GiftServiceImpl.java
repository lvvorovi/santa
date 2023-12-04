package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import Secret.Santa.Secret.Santa.validationUnits.GroupUtils;
import Secret.Santa.Secret.Santa.validationUnits.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftServiceImpl implements IGiftService {
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
        return iGiftRepo.findAll();
    }

    @Override
    public Gift getGiftById(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);
        return optionalGift.orElseThrow(() -> new EntityNotFoundException("Gift not found with id " + giftId));
    }

    @Override
    public Gift createGift(GiftDTO giftDTO) {
        if (giftDTO.getPrice() < 0) {
            throw new SantaValidationException("Price cannot be negative", "price", "NegativeValue", String.valueOf(giftDTO.getPrice()));
        }

        Gift gift = new Gift();
        gift.setName(giftDTO.getName());
        gift.setDescription(giftDTO.getDescription());
        gift.setLink(giftDTO.getLink());
        gift.setPrice(giftDTO.getPrice());

        userUtils.getUserById(giftDTO.getCreatedBy());
        gift.setCreatedBy(giftDTO.getCreatedBy());

        Group group = groupUtils.getGroupById(giftDTO.getGroupId());
        gift.setGroup(group);
        //gift.setGroup(giftDTO.getGroup());

        return iGiftRepo.save(gift);
    }

    @Override
    public GiftDTO updateGift(int giftId, GiftDTO giftDTO) {

        if (!iGiftRepo.existsById(giftId)) {
            throw new EntityNotFoundException("Gift not found with id " + giftId);
        }

        Gift requestEntity = giftMapper.toGift(giftDTO);
        Optional<Gift> existingGift = iGiftRepo.findById(giftId);

        Gift savedEntity = existingGift.get();
        savedEntity.setName(requestEntity.getName());
        savedEntity.setDescription(requestEntity.getDescription());
        savedEntity.setLink(requestEntity.getLink());
        savedEntity.setPrice(requestEntity.getPrice());

        userUtils.getUserById(requestEntity.getCreatedBy());
        savedEntity.setCreatedBy(requestEntity.getCreatedBy());

        Group group = groupUtils.getGroupById(giftDTO.getGroupId());
        savedEntity.setGroup(group);
        //savedEntity.setGroup(requestEntity.getGroup());

        savedEntity = iGiftRepo.save(savedEntity);

        return giftMapper.toGiftDTO(savedEntity);
    }


    @Override
    public void deleteGift(int giftId) {
        if (!iGiftRepo.existsById(giftId)) {
            throw new EntityNotFoundException("Gift not found with id " + giftId);
        }
        iGiftRepo.deleteById(giftId);
    }

    public List<Gift> getGiftsCreatedBy(int userId) {
        return iGiftRepo.findByCreatedBy(userId);
    }
}
