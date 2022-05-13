package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 사이에 벽 있냐없냐 -> 3차원 배열로 저장함
// bfs할 때 num을 빼야되는데 더했었네..(5에서 0까지 가야되는데)
// 가장 바깥쪽 칸...
public class BJ23289_온풍기안녕 {
    static int R, C, K, W, ans;
    static int[][] map, airBlowerTemp;
    static ArrayList<int[]> tempPos, airBlowerPos;
    static boolean[][][] walls;
    static boolean[][] visited;
    static int[] changeDir = {1, 3, 0, 2};
    static int[] di = {-1, 0, 1, 0};   // 상우하좌
    static int[] dj = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        init();
        setAirBlowerTemp();
        performanceTest();

        System.out.println(ans);
    }

    private static void performanceTest() {
        while(true) {
            ans++;
            blowAirBlower();
            controlTemperate();
            downTemperate();
            if(checkTemperate()) break;
            if(ans > 100) break;
        }
    }

    private static boolean checkTemperate() {
        boolean isPass = true;
        for(int[] pos : tempPos) {
            if(map[pos[0]][pos[1]] < K) {
                isPass = false;
                break;
            }
        }
        return isPass;
    }

        private static void downTemperate() {
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                if((i == 0 || i == R - 1 || j == 0 || j == C - 1) && map[i][j] >= 1) map[i][j]--;
            }
        }
    }

    private static void controlTemperate() {
        int[][] newMap = new int[R][C];
        for(int i = 0 ; i < R ; i++) {
            newMap[i] = map[i].clone();
        }
        boolean[][][] isGo = new boolean[R][C][4];
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                int now = map[i][j];
                for(int d = 0 ; d < 4 ; d++) {
                    int ni = i + di[d];
                    int nj = j + dj[d];
                    if(isOutOfIdx(ni, nj) || isGo[i][j][d] || walls[i][j][d]) continue;
                    int sub = Math.abs((map[i][j] - map[ni][nj]) / 4);
                    if(map[ni][nj] < now) {
                        newMap[ni][nj] += sub;
                        newMap[i][j] -= sub;
                    } else {
                        newMap[ni][nj] -= sub;
                        newMap[i][j] += sub;
                    }
                    isGo[i][j][d] = isGo[ni][nj][(d + 2) % 4] = true;
                }
            }
        }
        map = newMap;
    }

    private static void blowAirBlower() {
        for(int i = 0 ; i < R ; i++) {
            for(int j = 0 ; j < C ; j++) {
                map[i][j] += airBlowerTemp[i][j];
            }
        }
    }

    private static void setAirBlowerTemp() {
        for(int[] airBlower : airBlowerPos) {
            int d = airBlower[2];
            int ni = di[d] + airBlower[0];
            int nj = dj[d] + airBlower[1];
            if(isOutOfIdx(ni, nj) || walls[airBlower[0]][airBlower[1]][d]) continue;
            visited = new boolean[R][C];
            dfs1(ni, nj, airBlower[2], 5);
        }
    }

    private static void dfs1(int i, int j, int d, int num) {
        if(num == 0) return;
        visited[i][j] = true;
        airBlowerTemp[i][j] += num;
        int leftD = (d + 3) % 4;
        int rightD = (d + 1) % 4;
        int ni, nj;
        ni = i + di[leftD] + di[d];
        nj = j + dj[leftD] + dj[d];
        if(!isOutOfIdx(i + di[leftD], j + dj[leftD]) && !isOutOfIdx(ni, nj) && !walls[i][j][leftD] && !walls[i + di[leftD]][j + dj[leftD]][d]
                && !visited[ni][nj]) {
            dfs1(ni, nj, d, num - 1);
        }
        ni = i + di[d];
        nj = j + dj[d];
        if(!isOutOfIdx(ni, nj) && !walls[i][j][d] && !visited[ni][nj]) {
            dfs1(ni, nj, d, num - 1);
        }
        ni = i + di[rightD] + di[d];
        nj = j + dj[rightD] + dj[d];
        if(!isOutOfIdx(i + di[rightD], j + dj[rightD]) && !isOutOfIdx(ni, nj) && !walls[i][j][rightD] && !walls[i + di[rightD]][j + dj[rightD]][d]
                && !visited[ni][nj]) {
            dfs1(ni, nj, d, num - 1);
        }
    }

    static boolean isOutOfIdx(int i, int j) {
        return i < 0 || i >= R || j < 0 || j >= C;
    }
    static void init() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[R][C];
        airBlowerTemp = new int[R][C];
        tempPos = new ArrayList<>();
        airBlowerPos = new ArrayList<>();
        walls = new boolean[R][C][4];
        for(int i = 0 ; i < R ; i++) {
            st = new StringTokenizer(in.readLine(), " ");
            for(int j = 0 ; j < C ; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(1 <= num && num <= 4)
                    airBlowerPos.add(new int[]{i, j, changeDir[num - 1]});
                else if(num == 5) {
                    tempPos.add(new int[]{i, j});
                }
            }
        }
        W = Integer.parseInt(in.readLine());
        for(int i = 0 ; i < W ; i++) {
            st = new StringTokenizer(in.readLine(), " ");
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());
            if(t == 0) {
                walls[x - 1][y][2] = true;      // 아래방향
                walls[x][y][0] = true;             // 위방향에 벽
            } else if(t == 1){
                walls[x][y][1] = true;           // 오른쪽
                walls[x][y + 1][3] = true;       // 왼쪽
            }
        }
    }
}