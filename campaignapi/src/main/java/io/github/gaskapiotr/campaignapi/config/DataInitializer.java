package io.github.gaskapiotr.campaignapi.config;

import io.github.gaskapiotr.campaignapi.model.EmeraldAccount;
import io.github.gaskapiotr.campaignapi.model.Keyword;
import io.github.gaskapiotr.campaignapi.model.Town;
import io.github.gaskapiotr.campaignapi.repository.EmeraldAccountRepository;
import io.github.gaskapiotr.campaignapi.repository.KeywordRepository;
import io.github.gaskapiotr.campaignapi.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TownRepository townRepository;
    private final KeywordRepository keywordRepository;
    private final EmeraldAccountRepository emeraldAccountRepository;

    @Override
    public void run(String @NonNull ... args) {
        initEmeraldAccount();
        initTowns();
        initKeywords();
        log.info("Database initialized successfully with data.");
    }

    private void initEmeraldAccount() {
        if (emeraldAccountRepository.count() == 0) {
            EmeraldAccount account = new EmeraldAccount();
            account.setBalance(new BigDecimal("10000.00"));
            emeraldAccountRepository.save(account);
        }
    }

    private void initTowns() {
        if (townRepository.count() == 0) {
            List<Town> towns = List.of(
                    new Town("Kraków"),
                    new Town("Warszawa"),
                    new Town("Wrocław"),
                    new Town("Poznań"),
                    new Town("Gdańsk"),
                    new Town("Katowice"),
                    new Town("Szczecin"),
                    new Town("Lublin"),
                    new Town("Białystok")
            );
            townRepository.saveAll(towns);
        }
    }

    private void initKeywords() {
        if (keywordRepository.count() == 0) {
            List<Keyword> keywords = List.of(
                    new Keyword("electronics"),
                    new Keyword("smartphone"),
                    new Keyword("gaming"),
                    new Keyword("fashion"),
                    new Keyword("shoes"),
                    new Keyword("books"),
                    new Keyword("home"),
                    new Keyword("garden"),
                    new Keyword("sports"),
                    new Keyword("fitness"),
                    new Keyword("automotive"),
                    new Keyword("beauty"),
                    new Keyword("toys"),
                    new Keyword("music"),
                    new Keyword("food")
            );
            keywordRepository.saveAll(keywords);
        }
    }
}
