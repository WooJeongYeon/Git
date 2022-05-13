function solution(nums) {
  let sumList = [];
  for (let i = 0; i < nums.length - 2; i++) {
    for (let j = i + 1; j < nums.length - 1; j++) {
      for (let k = j + 1; k < nums.length; k++) {
        sumList.push(nums[i] + nums[j] + nums[k]);
      }
    }
  }
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
