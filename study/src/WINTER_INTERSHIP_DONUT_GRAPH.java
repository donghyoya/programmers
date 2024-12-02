import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WINTER_INTERSHIP_DONUT_GRAPH {

    private static class Solution{
        // 그래프를 저장할 리스트. 인접 리스트로 구현됨.
        private static List<List<Integer>> graph;

        // 방문 여부를 저장하는 배열
        private static boolean[] visited;

        // 시작 정점 (특정 조건을 만족하는 정점)
        private static int startVertex;

        // 그래프 내 최대 정점 번호
        private static int maxVertex;

        // 시작 정점에서 나가는 간선의 개수
        private static int graphNum;

        // 각 정점으로 들어오는 간선의 개수를 저장
        private static int[] incomingEdges;

        // 그래프를 초기화하는 메서드
        private void initGraph(int[][] edges) {
            maxVertex = 0;

            // 간선 정보를 통해 최대 정점 번호를 계산
            for (int[] edge : edges) {
                maxVertex = Math.max(maxVertex, Math.max(edge[0], edge[1]));
            }

            // 방문 배열, 들어오는 간선 배열, 그래프 리스트 초기화
            visited = new boolean[maxVertex + 1];
            incomingEdges = new int[maxVertex + 1];
            graph = new ArrayList<>(maxVertex + 1);

            // 각 정점에 대해 인접 리스트 생성
            for (int i = 0; i <= maxVertex; i++) {
                graph.add(new LinkedList<>());
            }

            // 간선 정보를 기반으로 그래프 구성
            for (int i = 0; i < edges.length; i++) {
                graph.get(edges[i][0]).add(edges[i][1]); // 단방향 그래프
                incomingEdges[edges[i][1]]++; // 도착 정점의 들어오는 간선 수 증가
            }
        }

        // 그래프 분석 및 결과를 반환하는 메서드
        public int[] solution(int[][] edges) {
            int[] answer = new int[4];

            // 그래프 초기화
            initGraph(edges);

            // 시작 정점을 찾고, 전체 그래프의 간선 수를 계산
            startVertex = findCreatedVertex();
            graphNum = graph.get(startVertex).size();
            answer[0] = startVertex;

            // 시작 정점과 관련된 간선 제거
            removeEdgesFromCreatedVertex(startVertex);

            // 막대 그래프의 개수 계산
            // 나가는 간선이 없는 정점의 개수를 찾음
            answer[2] = countBarGraphs();

            // 8자 모양 그래프의 개수 계산
            // 들어오는 간선 2개, 나가는 간선 2개인 정점을 찾음
            answer[3] = countEightShape();

            // 나머지 그래프 개수 계산 (총 그래프 수 - 막대 그래프 - 8자 그래프)
            answer[1] = graphNum - (answer[2] + answer[3]);

            return answer;
        }

        // 막대 그래프의 개수를 계산
        private int countBarGraphs() {
            int count = 0;
            for (int i = 1; i < graph.size(); i++) {
                if (i == startVertex) {
                    continue;
                }
                if (graph.get(i).isEmpty()) { // 나가는 간선이 없는 경우
                    count++;
                    visited[i] = true;
                }
            }
            return count;
        }

        // 8자 모양 그래프의 개수를 계산
        private int countEightShape() {
            int count = 0;
            for (int i = 1; i < graph.size(); i++) {
                if (!visited[i]) {
                    // 나가는 간선이 2개, 들어오는 간선이 2개인 정점을 찾음
                    if (graph.get(i).size() == 2 && countIncomingEdges(i) == 2) {
                        count++;
                    }
                }
            }
            return count;
        }

        // 시작 정점(특정 조건을 만족하는 정점)을 찾음
        private int findCreatedVertex() {
            int createdVertex = -1;
            for (int i = 1; i < graph.size(); i++) {
                // 들어오는 간선이 없고, 나가는 간선이 2개 이상인 정점
                if (graph.get(i).size() >= 2 && countIncomingEdges(i) == 0) {
                    createdVertex = i;
                    break;
                }
            }
            visited[createdVertex] = true; // 방문 표시
            return createdVertex;
        }

        // 특정 정점에 들어오는 간선의 개수를 반환
        private int countIncomingEdges(int vertex) {
            return incomingEdges[vertex];
        }

        // 시작 정점에서 나가는 간선을 제거
        private void removeEdgesFromCreatedVertex(int vertex) {
            for (int end : graph.get(vertex)) {
                incomingEdges[end]--; // 연결된 정점의 들어오는 간선 수 감소
            }
            graph.set(vertex, new LinkedList<>()); // 시작 정점의 간선을 비움
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // 예제 입력
        int[][] edges = {
                {1, 2},
                {1, 3},
                {2, 4},
                {3, 5},
                {5, 2},
                {4, 6},
                {5, 6}
        };

        // 솔루션 호출
        int[] result = solution.solution(edges);

        // 결과 출력
        System.out.println("시작 정점: " + result[0]);
        System.out.println("나머지 그래프 개수: " + result[1]);
        System.out.println("막대 그래프 개수: " + result[2]);
        System.out.println("8자 모양 그래프 개수: " + result[3]);
    }
}
