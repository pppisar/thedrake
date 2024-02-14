package cz.cvut.fit.thedrakefx.logic;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable {
    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        troopMap = Collections.emptyMap();
        leaderPosition = TilePos.OFF_BOARD;
        guards = 0;
    }

    public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap,
                       TilePos leaderPosition, int guards) {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;

    }

    public Optional<TroopTile> at(TilePos pos) {
        TroopTile res = troopMap.get(pos);
        if (res == null) {
            return Optional.empty();
        }

        return Optional.of(res);
    }

    public PlayingSide playingSide() {
        return playingSide;
    }

    public TilePos leaderPosition() {
        return leaderPosition;
    }

    public int guards() {
        return guards;
    }

    public boolean isLeaderPlaced() {
        return leaderPosition != TilePos.OFF_BOARD;
    }

    public boolean isPlacingGuards() {
        return (isLeaderPlaced() && guards < 2);
    }

    public Set<BoardPos> troopPositions() {
        return troopMap.keySet();
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if (this.at(target).isPresent()) {
            throw new IllegalArgumentException();
        }
        Map<BoardPos, TroopTile> newMap = new HashMap<>(troopMap);
        newMap.put(target, new TroopTile(troop, playingSide, TroopFace.AVERS));
        TilePos newLeaderPosition = leaderPosition;
        int newGuards = guards;
        if (! isLeaderPlaced()) {
            newLeaderPosition = target;
        } else if(this.isPlacingGuards()) {
            newGuards++;
        }
        return new BoardTroops(playingSide, newMap, newLeaderPosition, newGuards);
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException("Cannot move troops before the leader is placed.");
        } else if (isPlacingGuards()) {
            throw new IllegalStateException("Cannot move troops before guards are placed.");
        } else if (at(origin).isEmpty() || at(target).isPresent()) {
            throw new IllegalArgumentException();
        } else {
            TroopTile targetTroop = troopMap.get(origin).flipped();
            Map<BoardPos, TroopTile> newMap = new HashMap<>(troopMap);
            newMap.remove(origin);
            newMap.put(target, targetTroop);
            if (origin.equals(leaderPosition)) {
                return new BoardTroops(playingSide, newMap, target, guards);
            }
            return new BoardTroops(playingSide, newMap, leaderPosition, guards);
        }
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (at(origin).isEmpty())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException("Cannot move troops before the leader is placed.");
        } else if (isPlacingGuards()) {
            throw new IllegalStateException("Cannot move troops before guards are placed.");
        } else if (at(target).isEmpty()) {
            throw new IllegalArgumentException();
        } else  {
            Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
            TroopTile tile = newTroops.remove(target);
            if (leaderPosition.equals(target)) {
                return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
            }
            return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
        }
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");

        writer.print("\"side\":");
        playingSide.toJSON(writer);
        writer.print(",");

        writer.print("\"leaderPosition\":");
        leaderPosition.toJSON(writer);
        writer.print(",");

        writer.print("\"guards\":" + guards);
        writer.print(",");

        List<BoardPos> troopPositions = new ArrayList<>(troopPositions());
        troopPositions.sort(BoardPos::compareTo);
        writer.print("\"troopMap\":{");
        for (BoardPos bPos: troopPositions) {
            bPos.toJSON(writer);
            writer.print(":");
            troopMap.get(bPos).toJSON(writer);
            if (bPos != troopPositions.get(troopPositions.size() - 1))
                writer.print(",");
        }
        writer.print("}");

        writer.print("}");
    }
}
