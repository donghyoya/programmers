import java.util.*;

/**
 * 2024-11-16(토)
 * 사이트: school.programmers.co.kr/learn/courses/30/lessons/258712
 * 가장 많은 받은 선물 문제
 * 입출력: friends: ["muzi", "ryan", "frodo", "neo"]
 *        gifts: ["muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"]
 *
 */
public class WINTER_INTERSHIP_MOSTGIFT {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] friends = new String[100];
        String[] gifts = new String[100];
        solution.solution(friends, gifts);
    }

    private static class Solution {
        public int solution(String[] friends, String[] gifts) {
            int n = friends.length;
            Map<String, Integer> friendIndex = new HashMap<>();
            for (int i = 0; i < n; i++) {
                friendIndex.put(friends[i], i);
            }

            int[][] giftCount = new int[n][n];
            int[] giftIndex = new int[n];

            for (String gift : gifts) {
                String[] parts = gift.split(" ");
                int giver = friendIndex.get(parts[0]);
                int receiver = friendIndex.get(parts[1]);
                giftCount[giver][receiver]++;
            }

            for (int i = 0; i < n; i++) {
                int given = 0, received = 0;
                for (int j = 0; j < n; j++) {
                    given += giftCount[i][j];
                    received += giftCount[j][i];
                }
                giftIndex[i] = given - received;
            }

            int[] nextMonthGifts = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (giftCount[i][j] > giftCount[j][i]) {
                        nextMonthGifts[i]++;
                    } else if (giftCount[i][j] < giftCount[j][i]) {
                        nextMonthGifts[j]++;
                    } else {
                        if (giftIndex[i] > giftIndex[j]) {
                            nextMonthGifts[i]++;
                        } else if (giftIndex[i] < giftIndex[j]) {
                            nextMonthGifts[j]++;
                        }
                    }
                }
            }

            int maxGifts = 0;
            for (int giftsReceived : nextMonthGifts) {
                if (giftsReceived > maxGifts) {
                    maxGifts = giftsReceived;
                }
            }

            return maxGifts;
        }
    }
}
