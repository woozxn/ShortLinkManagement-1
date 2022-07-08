package com.dreaming.shortlink.service;

import com.dreaming.shortlink.common.domain.ShortLink;
import com.dreaming.shortlink.common.item.ShortLinkItemDto;
import com.dreaming.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShortLinkService {

    private final ShortLinkRepository shortLinkRepository;

    public ShortLinkItemDto findShortLinkById(String shortId) {

        ShortLink entity = shortLinkRepository.findByShortId(shortId)
                .orElseThrow(() -> new RuntimeException());
        return new ShortLinkItemDto(entity);
    }


    public void saveShortLink(ShortLink shortLink) {
        shortLinkRepository.save(shortLink);
    }


    public String generateShortId(String url) {

        //중복 확인 로직 추가해야함
        //shortLinkRepository.findByUrl(url);

        int leftLimit = 48; //0
        int rightLimit = 122; //z
        int stringLength = 5;
        Random random = new Random();

        String shortId = random.ints(leftLimit, rightLimit + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return shortId;
    }

    public String getUrlByShortId(String shortId) {
        return shortLinkRepository.findByShortId(shortId)
                .orElseThrow(() -> new RuntimeException()).getUrl();
        //null 이면 RuntimeException, 아니면 getUrl() 실행
    }

}
