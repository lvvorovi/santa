package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.exception.SantaValidationException;
import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.User;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements IGiftService {
    private final GiftMapper giftMapper;
    @Autowired
    IGiftRepo iGiftRepo;

    @Override
    public List<GiftDTO> getAllGifts() {
        List<Gift> users = iGiftRepo.findAll();

        return users.stream()
                .map(giftMapper::toGiftDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GiftDTO getGiftById(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);

        if (optionalGift.isPresent()) {
            Gift gift = optionalGift.get();
            return giftMapper.toGiftDTO(gift);
        }

        throw new EntityNotFoundException("Gift not found with id " + giftId);
    }

    @Override
    public GiftDTO createGift(GiftDTO giftDTO) {
        if (giftDTO.getPrice() < 0) {
            throw new SantaValidationException("Price cannot be negative", "price", "NegativeValue", String.valueOf(giftDTO.getPrice()));
        }
        if (giftDTO == null) {
            throw new IllegalArgumentException("GiftDTO cannot be null");
        }

        Gift gift = new Gift();
        gift.setName(giftDTO.getName());
        gift.setDescription(giftDTO.getDescription());
        gift.setLink(giftDTO.getLink());
        gift.setPrice(giftDTO.getPrice());
        gift.setGroup(giftDTO.getGroup());
        Gift savedGift = iGiftRepo.save(gift);
        return giftMapper.toGiftDTO(savedGift);
    }

    @Override
    public GiftDTO updateGift(GiftDTO giftDTO) {
        if (giftDTO == null) {
            throw new IllegalArgumentException("GiftDTO cannot be null");
        }
        if (giftDTO.getGiftId() == null){
            throw new IllegalArgumentException("This gift does not have ID");
        }
        Optional<Gift> existingGift = iGiftRepo.findById(giftDTO.getGiftId());
        if (existingGift.isPresent()) {
            Gift gift = giftMapper.toGift(giftDTO);
            Gift updatedGift = iGiftRepo.save(gift);
            return giftMapper.toGiftDTO(updatedGift);
        }
        throw new EntityNotFoundException("User not found with id " + giftDTO.getGiftId());
    }


    @Override
    public boolean deleteGift(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);
        if (optionalGift.isPresent()) {
            try {
                iGiftRepo.deleteById(giftId);
            } catch (Exception exception) {
                return false;
            }
            return true;
        }
        throw new EntityNotFoundException("Group not found with id " + giftId);
    }
}
