function solution(array, commands) {
  return commands.map(
    (com) =>
      array.slice(com[0] - 1, com[1]).sort(function (a, b) {
        return a - b;
      })[com[2] - 1]
  );
}

// 그냥 일반적으로 sort 를 사용하면, 대소비교가 제대로 되지 않음
// => 비교함수 필요
// https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/sort#%EC%98%88%EC%A0%9C
