import java.util.*;

public class DejarikAI {
    private PolygonPanel panel;

    public DejarikAI(PolygonPanel pp) {
        panel = pp;
    }

    public AIMove chooseFirstAction(Spot[] board) {
        panel.initializeValidSpotMoves();
        panel.initializeValidAttackMoves();

        List<AIMove> candidates = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Piece p = board[i].getPiece();
            if (p.getClass() != Void.class && !p.isRed()) {
                candidates.addAll(enumerateAttacks(i, board));
                candidates.addAll(enumerateMoves(i, board));
            }
        }

        if (candidates.isEmpty()) return null;
        return Collections.max(candidates, Comparator.comparingInt(m -> m.score));
    }

    public AIMove chooseSecondAction(Spot[] board, String currentType) {
        panel.initializeValidSpotMoves();
        panel.initializeValidAttackMoves();

        int fromIndex = findPieceByType(board, currentType);
        if (fromIndex == -1) return null;

        List<AIMove> candidates = new ArrayList<>();
        candidates.addAll(enumerateAttacks(fromIndex, board));
        candidates.addAll(enumerateMoves(fromIndex, board));

        if (candidates.isEmpty()) return null;
        return Collections.max(candidates, Comparator.comparingInt(m -> m.score));
    }

    private List<AIMove> enumerateMoves(int from, Spot[] board) {
        List<AIMove> moves = new ArrayList<>();
        int[] validSpaces = board[from].getWhichSpace();
        if (validSpaces == null) return moves;

        for (int to : validSpaces) {
            if (board[to].getPiece().getClass() == Void.class) {
                moves.add(new AIMove(from, to, false, scoreMoveAction(from, to, board)));
            }
        }
        return moves;
    }

    private List<AIMove> enumerateAttacks(int from, Spot[] board) {
        List<AIMove> attacks = new ArrayList<>();
        int[] attackSpaces = board[from].getAttackSpace();
        if (attackSpaces == null) return attacks;

        Piece attacker = board[from].getPiece();
        for (int to : attackSpaces) {
            Piece target = board[to].getPiece();
            if (target.getClass() != Void.class && target.isRed()) {
                attacks.add(new AIMove(from, to, true, scoreAttackAction(attacker, target, board)));
            }
        }
        return attacks;
    }

    private int scoreAttackAction(Piece attacker, Piece target, Spot[] board) {
        float prob = killProbability(attacker.getAttack(), target.getDefense());
        int targetValue = target.getAttack() + target.getDefense();
        int score = (int)(prob * targetValue * 100);

        int redCount = 0;
        for (Spot s : board) {
            if (s.getPiece().getClass() != Void.class && s.getPiece().isRed()) redCount++;
        }
        if (redCount == 1) score += 500;

        return score;
    }

    private int scoreMoveAction(int from, int to, Spot[] board) {
        int distBefore = minDistToEnemy(from, board);
        int distAfter = minDistToEnemy(to, board);

        int score = 50 - (distAfter * 10);

        int[] attackSpaces = board[to].getAttackSpace();
        if (attackSpaces != null) {
            for (int at : attackSpaces) {
                if (board[at].getPiece().getClass() != Void.class && board[at].getPiece().isRed()) {
                    score += 20;
                }
            }
        }

        if (distAfter > distBefore) score -= 15;

        return score;
    }

    private float killProbability(int atk, int def) {
        if (atk == 0 || def == 0) return 0;
        int wins = 0;
        for (int a = 1; a <= atk; a++) {
            for (int d = 1; d <= def; d++) {
                if (a > d) wins++;
            }
        }
        return (float) wins / (atk * def);
    }

    private int minDistToEnemy(int spotIndex, Spot[] board) {
        int[] dist = new int[25];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[spotIndex] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(spotIndex);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbor : getNeighbors(curr)) {
                if (dist[neighbor] == Integer.MAX_VALUE) {
                    dist[neighbor] = dist[curr] + 1;
                    Piece p = board[neighbor].getPiece();
                    if (p.getClass() != Void.class && p.isRed()) {
                        return dist[neighbor];
                    }
                    queue.add(neighbor);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private int[] getNeighbors(int spot) {
        if (spot <= 11) {
            return new int[]{(spot + 1) % 12, (spot + 11) % 12, spot + 12};
        } else if (spot <= 23) {
            int inner = spot - 12;
            return new int[]{12 + (inner + 1) % 12, 12 + (inner + 11) % 12, 24, spot - 12};
        } else {
            return new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        }
    }

    private int findPieceByType(Spot[] board, String type) {
        if (type == null) return -1;
        for (int i = 0; i < 25; i++) {
            Piece p = board[i].getPiece();
            if (type.equals(p.getType()) && !p.isRed()) {
                return i;
            }
        }
        return -1;
    }

    public static class AIMove {
        public int fromSpot;
        public int toSpot;
        public boolean isAttack;
        public int score;

        public AIMove(int from, int to, boolean attack, int score) {
            this.fromSpot = from;
            this.toSpot = to;
            this.isAttack = attack;
            this.score = score;
        }
    }
}
