function solution(nums) {
  const sum = [];
  const getComb = (select, arr) => {
    const comb = [];
    // 베이스 캠프
    if (select === 1) return arr.map((a) => [a]);
    // 재귀
    arr.forEach((val, i, array) => {
      const combination = getComb(select - 1, array.slice(i + 1));
      const combList = combination.map((comb) => [val, ...comb]);
      comb.push(...combList);
    });
    return comb;
  };
  const sumList = getComb(3, nums).map((combs) =>
    combs.reduce((prev, curv) => prev + curv)
  );
  let cnt = 0;
  for (candidate of sumList) {
    let measure = false;
    for (let i = 2; i <= Math.ceil(Math.sqrt(candidate)); i++) {
      if (candidate % i === 0) {
        measure = true;
        break;
      }
    }
    if (!measure) {
      cnt += 1;
    }
  }
  return cnt;
}
