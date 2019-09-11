113. Path Sum II

find all root-to-leaf paths where each path‘s sum equals the given sum.

      5’
     / \
    4‘  8’
   /   / \
  11’ 13  4‘
 /  \    / \
7    2‘ 5’   1

[
   [5,4,11,2],
   [5,8,4,5]
]

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// dfs but how to print the path?
// what dfs() should return ? maybe nothing, we just need to traverse and modify the global variable(ans) 
// what params of dfs? node, now sum, target, path, ans




public class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        dfs(root, target, new ArrayList<>(), ans);
        return ans;
    }

    public void dfs(TreeNode root, int target, List<Integer> path, List<List<Integer>> ans) {
        if (root == null) {
            return;
        }
        path.add(root.val);

        if (root.left == null && root.right == null) {
            if (root.val == target) {
                ans.add(new ArrayList<Integer>(path));
                return;
            }
        }

        if (root.left != null) {
            dfs(root.left, target - root.val, path,ans);
            path.remove(path.size() - 1);            
        }

        if (root.right != null) {
            dfs(root.right, target - root.val, path, ans);
            path.remove(path.size() - 1);
        }
    }
}




O(N)
O(logN)