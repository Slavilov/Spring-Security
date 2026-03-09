package com.example.footballpairs.service;

import com.example.footballpairs.model.FootballMatch;
import com.example.footballpairs.model.ParticipationRecord;
import com.example.footballpairs.model.Player;
import com.example.footballpairs.model.Team;
import com.example.footballpairs.repository.FootballMatchRepository;
import com.example.footballpairs.repository.ParticipationRecordRepository;
import com.example.footballpairs.repository.PlayerRepository;
import com.example.footballpairs.repository.TeamRepository;
import com.example.footballpairs.util.CsvReaderUtil;
import com.example.footballpairs.util.DateParserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class CsvImportService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final FootballMatchRepository footballMatchRepository;
    private final ParticipationRecordRepository participationRecordRepository;

    @Value("${app.csv.teams-path}")
    private String teamsPath;

    @Value("${app.csv.players-path}")
    private String playersPath;

    @Value("${app.csv.matches-path}")
    private String matchesPath;

    @Value("${app.csv.records-path}")
    private String recordsPath;

    public CsvImportService(TeamRepository teamRepository,
                            PlayerRepository playerRepository,
                            FootballMatchRepository footballMatchRepository,
                            ParticipationRecordRepository participationRecordRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.footballMatchRepository = footballMatchRepository;
        this.participationRecordRepository = participationRecordRepository;
    }

    @Transactional
    public void importAllIfDatabaseEmpty() throws IOException {
        if (teamRepository.count() > 0 || playerRepository.count() > 0
                || footballMatchRepository.count() > 0 || participationRecordRepository.count() > 0) {
            return;
        }

        importAll();
    }

    @Transactional
    public void importAll() throws IOException {
        participationRecordRepository.deleteAllInBatch();
        footballMatchRepository.deleteAllInBatch();
        playerRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();

        importTeams();
        importPlayers();
        importMatches();
        importRecords();
    }

    private void importTeams() throws IOException {
        List<String[]> rows = CsvReaderUtil.readRows(teamsPath);

        for (String[] row : rows) {
            Team team = new Team();
            team.setId(parseLong(row[0]));
            team.setName(row[1]);
            team.setManagerFullName(row[2]);
            team.setGroupName(row[3]);
            teamRepository.save(team);
        }
    }

    private void importPlayers() throws IOException {
        List<String[]> rows = CsvReaderUtil.readRows(playersPath);

        for (String[] row : rows) {
            Team team = teamRepository.findById(parseLong(row[4]))
                    .orElseThrow(() -> new IllegalArgumentException("Missing team for player: " + row[0]));

            Player player = new Player();
            player.setId(parseLong(row[0]));
            player.setTeamNumber(parseInteger(row[1]));
            player.setPosition(row[2]);
            player.setFullName(row[3]);
            player.setTeam(team);
            playerRepository.save(player);
        }
    }

    private void importMatches() throws IOException {
        List<String[]> rows = CsvReaderUtil.readRows(matchesPath);

        for (String[] row : rows) {
            Team aTeam = teamRepository.findById(parseLong(row[1]))
                    .orElseThrow(() -> new IllegalArgumentException("Missing A team for match: " + row[0]));
            Team bTeam = teamRepository.findById(parseLong(row[2]))
                    .orElseThrow(() -> new IllegalArgumentException("Missing B team for match: " + row[0]));

            FootballMatch match = new FootballMatch();
            match.setId(parseLong(row[0]));
            match.setTeamA(aTeam);
            match.setTeamB(bTeam);
            match.setDate(DateParserUtil.parseDate(row[3]));
            match.setScore(row[4]);
            footballMatchRepository.save(match);
        }
    }

    private void importRecords() throws IOException {
        List<String[]> rows = CsvReaderUtil.readRows(recordsPath);

        for (String[] row : rows) {
            Player player = playerRepository.findById(parseLong(row[1]))
                    .orElseThrow(() -> new IllegalArgumentException("Missing player for record: " + row[0]));
            FootballMatch match = footballMatchRepository.findById(parseLong(row[2]))
                    .orElseThrow(() -> new IllegalArgumentException("Missing match for record: " + row[0]));

            ParticipationRecord record = new ParticipationRecord();
            record.setId(parseLong(row[0]));
            record.setPlayer(player);
            record.setMatch(match);
            record.setFromMinute(parseInteger(row[3]));
            record.setToMinute(parseEndMinute(row[4]));
            participationRecordRepository.save(record);
        }
    }

    private Long parseLong(String value) {
        return Long.parseLong(value.trim());
    }

    private Integer parseInteger(String value) {
        return Integer.parseInt(value.trim());
    }

    private Integer parseEndMinute(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("NULL")) {
            return 90;
        }
        return parseInteger(value);
    }
}
