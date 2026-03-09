package com.example.footballpairs.service;

import com.example.footballpairs.dto.MatchOverlapDto;
import com.example.footballpairs.dto.PairResultDto;
import com.example.footballpairs.model.FootballMatch;
import com.example.footballpairs.model.ParticipationRecord;
import com.example.footballpairs.model.Player;
import com.example.footballpairs.model.PlayerPairKey;
import com.example.footballpairs.repository.FootballMatchRepository;
import com.example.footballpairs.repository.ParticipationRecordRepository;
import com.example.footballpairs.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PairAnalysisService {

    private final FootballMatchRepository footballMatchRepository;
    private final ParticipationRecordRepository participationRecordRepository;
    private final PlayerRepository playerRepository;

    public PairAnalysisService(FootballMatchRepository footballMatchRepository,
                               ParticipationRecordRepository participationRecordRepository,
                               PlayerRepository playerRepository) {
        this.footballMatchRepository = footballMatchRepository;
        this.participationRecordRepository = participationRecordRepository;
        this.playerRepository = playerRepository;
    }

    public List<PairResultDto> getAllPairsSortedBySharedMinutes() {
        List<FootballMatch> matches = footballMatchRepository.findAll();
        Map<PlayerPairKey, Integer> totalMinutesByPair = new HashMap<>();
        Map<PlayerPairKey, List<MatchOverlapDto>> detailsByPair = new HashMap<>();

        for (FootballMatch match : matches) {
            List<ParticipationRecord> records = participationRecordRepository.findByMatchId(match.getId());

            for (int i = 0; i < records.size(); i++) {
                for (int j = i + 1; j < records.size(); j++) {
                    ParticipationRecord first = records.get(i);
                    ParticipationRecord second = records.get(j);
                    int overlap = calculateOverlap(first, second);

                    if (overlap <= 0) {
                        continue;
                    }

                    PlayerPairKey key = new PlayerPairKey(first.getPlayer().getId(), second.getPlayer().getId());
                    totalMinutesByPair.put(key, totalMinutesByPair.getOrDefault(key, 0) + overlap);
                    detailsByPair.computeIfAbsent(key, ignored -> new ArrayList<>())
                            .add(new MatchOverlapDto(match.getId(), match.getDisplayTitle(), overlap));
                }
            }
        }

        Map<Long, Player> playersById = new HashMap<>();
        for (Player player : playerRepository.findAll()) {
            playersById.put(player.getId(), player);
        }

        List<PairResultDto> results = new ArrayList<>();
        for (Map.Entry<PlayerPairKey, Integer> entry : totalMinutesByPair.entrySet()) {
            PlayerPairKey key = entry.getKey();
            Player firstPlayer = playersById.get(key.getPlayer1Id());
            Player secondPlayer = playersById.get(key.getPlayer2Id());
            PairResultDto dto = new PairResultDto(
                    firstPlayer.getId(),
                    firstPlayer.getFullName(),
                    secondPlayer.getId(),
                    secondPlayer.getFullName(),
                    entry.getValue(),
                    detailsByPair.getOrDefault(key, new ArrayList<>())
            );
            dto.getMatchDetails().sort(Comparator.comparing(MatchOverlapDto::getMatchId));
            results.add(dto);
        }

        results.sort(Comparator.comparingInt(PairResultDto::getTotalSharedMinutes).reversed()
                .thenComparing(PairResultDto::getPlayer1Id)
                .thenComparing(PairResultDto::getPlayer2Id));

        return results;
    }

    public PairResultDto getTopPair() {
        List<PairResultDto> results = getAllPairsSortedBySharedMinutes();
        return results.isEmpty() ? null : results.get(0);
    }

    private int calculateOverlap(ParticipationRecord first, ParticipationRecord second) {
        int start = Math.max(first.getFromMinute(), second.getFromMinute());
        int end = Math.min(first.getToMinute(), second.getToMinute());
        return Math.max(0, end - start);
    }
}
