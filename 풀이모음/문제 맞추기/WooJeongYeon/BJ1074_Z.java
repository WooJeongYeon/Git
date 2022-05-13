// 시간초과 나서 r, c가 포함되는 영역으로 범위를 좁히고, 값 규칙을 찾아 구함
package day0817;

import java.util.Scanner;
/*
 * Date : 210817
 */
public class BJ1074_Z {
	static int result;			// 결과를 저장
	static int N, r, c;
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		N = in.nextInt();		// 2^N의 길이를 가지는 2차원 배열을 탐색
		r = in.nextInt();		// 찾고자 하는 행
		c = in.nextInt();		// 찾고자 하는 열
		z(N, 0, 0, 0);			// 해당 행과 열에 저장된 값을 찾는 메소드
		System.out.println(result);	// 결과를 출력
	}
	// n - 2^n의 길이를 가지는 배열
	// i - 해당 배열의 맨 위의 인덱스
	// j - 해당 배열의 맨 왼쪽 인덱스
	// value - 해당 배열의 맨 위의 왼쪽에 저장된 값
	static void z(int n, int i, int j, int value) {		// 분할정복! 재귀함수!
		if(n == 0) {		// n이 0이면 -> 길이 1인 2차원 배열(모두 분할됨)
//			System.out.println(i + " " + j);
			if(i == r && j == c)	// 해당 배열의 i, j 인덱스가 r, c와 같다면 해당 칸의 값을 결과로 저장
				result = value;
			return;					// 반환
		}
		int x = 1 << (n - 1);
		int downI = i + x;			// 4개의 분할된 칸에 대해 아래의 i 인덱스
		int rightJ = j + x;			// 4개의 분할된 칸에 대해 오른쪽의 j 인덱스
		int v = x * x;				// 더해줄 값
		// Z모양으로 탐색
		if(i <= r && r < downI && j <= c && c < rightJ) {	// 1 : r, c 인덱스가 왼쪽 위의 영역에 해당하면
			z(n - 1, i, j, value);			// 값을 그대로 사용
		}
		else if(i <= r && r < downI && c >= rightJ) {		// 2 : r, c 인덱스가 오른쪽 위의 영역에 해당하면
			z(n - 1, i, rightJ, value + v);	// 값에 v를 더해줌
		}
		else if(r >= downI && j <= c && c < rightJ) {		// 3 : r, c 인덱스가 왼쪽 아래 영역에 해당하면
			z(n - 1, downI, j, value + v * 2);	// 값에 v * 2를 더해줌
		}
		else if(r >= downI && c >= rightJ) {			// 4 : r, c 인덱스가 오른쪽 아래 영역에 해당하면
			z(n - 1, downI, rightJ, value + v * 3);	// 값에 v * 3을 더해줌
		}
	}
}
