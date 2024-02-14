package cz.cvut.fit.thedrakefx.logic;

import java.io.PrintWriter;

public class Board implements JSONSerializable {
    private final int dimension;

    private BoardTile[][] desk;

    public Board(int dimension) {
        this.dimension = dimension;
        this.desk = new BoardTile[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.desk[i][j] = BoardTile.EMPTY;
            }
        }
    }

    public Board(int dimension, BoardTile[][] desk) {
        this.dimension = dimension;
        this.desk = desk;
    }

    public int dimension() {
        return dimension;
    }

    public BoardTile at(TilePos pos) {
        return desk[pos.i()][pos.j()];
    }

    public Board withTiles(TileAt... ats) {
        BoardTile[][] newDesk = new BoardTile[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            newDesk[i] = this.desk[i].clone();
        }

        for (TileAt cTile: ats) {
            newDesk[cTile.pos.i()][cTile.pos.j()] = cTile.tile;
        }

        return new Board(dimension, newDesk);
    }

    public PositionFactory positionFactory() {
        return new PositionFactory(this.dimension);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");
        writer.print("\"dimension\":" + dimension + ",");

        writer.print("\"tiles\":[");
        for (int j = 0; j < dimension; j++) {
            for (int i = 0; i < dimension; i++) {
                desk[i][j].toJSON(writer);
                if (i != j || i != dimension - 1)
                    writer.print(",");
            }
        }
        writer.print("]}");
    }

    public static class TileAt {
        public final BoardPos pos;
        public final BoardTile tile;

        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }
}

