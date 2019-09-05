41. First Missing Positive

Given an unsorted integer array, find the smallest missing positive integer.

寻找从1开始第一个缺失的正数

Input: [1,2,0]
Output: 3

Input: [3,4,-1,1]
Output: 2

Input: [7,8,9,11,12]
Output: 1

Your algorithm should run in O(n) time and uses constant O(1) extra space.

Hint
1. how you would solve the problem in non-constant space. 
	Can you apply that logic to the existing space?
2. We don‘t care about duplicates or non-positive integers
3. Remember that O(2n) = O(n)


A few quick sort :
1. Sort all numbers and iterate through to find the first missing integer? 
	Nope, most sorting algorithms takes at least O(NlogN) time.
	// implement 
	class Solution {
	    public int firstMissingPositive(int[] nums) {
	        Arrays.sort(nums);
	        int cur = 0;
	        for (int i = 0; i < nums.length; i++) {
	            if (nums[i] < 0) continue;
	            if (nums[i] - cur > 1)
	                return cur + 1;
	            else 
	                cur = nums[i];
	        }
	        return cur + 1;
	    }
	}

2. how about use Linear sorting algorithm? 
	No, bucket sort requires O(N) space.
3. Mapping all positive integers to a hash table and iterate?
	No, hash table requires O(N) space.


------> then how we solve this ?

It is asking for the first missing POSITIVE integer.
So given a number in the array, 
	--> if it is non-positive, ignore it;
	--> if it is positive, say we have A[i] = x, we know it should be in slot A[x-1]
		   That is to say, we can swap A[x-1] with A[i] so as to place x into the right place.
		类比桶排序 用数组的下标做文章 
		因为要找第一个丢失的正数 所以 一个长度为 4的数组 最多存 1-4 所以答案就在 1-5 之间


use swapping to keep constant space and also make use of the length of the array


			3, 4, -1, 1
for 1	   
  while 1   -1, 4, 3, 1

for 2 
  while 1   -1, 1, 3, 4
  while 2   1, -1, 3, 4

for 3
for 4
end




class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        // one pass 1. move num to its position based on the index of the array
        //          2. eliminate the overbound nums
        
        for (int i = 0; i < nums.length; i++) {
        	//只判断 下标的话 nums[i] - 1 != i  这个 corner case 过不了 [1, 1] 
        	// nums[i]  只能在 1-nums.length 这个范围内
        	// 并且 因为可能有重复 所以要换到的位置的数字 不能和我现在的重复
            while (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        // 结束之后 数组下标对应的数 应该是 该下标 + 1
        // second pass
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            } 
        }
        return nums.length + 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}




另一种写法 while 不每次加

class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        // one pass 1. move num to its position based on the index of the array
        //          2. eliminate the overbound nums
        while (i < nums.length) {
        	//只判断 下标的话 nums[i] - 1 != i  这个 corner case 过不了 [1, 1] 
        	// nums[i]  只能在 1-nums.length 这个范围内
        	// 并且 因为可能有重复 所以要换到的位置的数字 不能和我现在的重复
            if (nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            } else {
            	i++;
            }
        }
        // 结束之后 数组下标对应的数 应该是 该下标 + 1
        // second pass
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            } 
        }
        return nums.length + 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}






