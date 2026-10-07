class Solution {
    public int removeDuplicates(int[] nums) {
        
        int n = nums.length;
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0;i<n;i++){
            if(i==0 || nums[i]!=nums[i-1]){
                list.add(nums[i]);
            }
        }
        for(int i=0; i<list.size(); i++) nums[i] = list.get(i);
        return list.size();
    }
}