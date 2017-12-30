/*
Design a simplified version of Twitter where users can post tweets, follow/unfollow another user
and is able to see the 10 most recent tweets in the user's news feed.
Your design should support the following methods:
    postTweet(userId, tweetId): Compose a new tweet.
    getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed.
    Each item in the news feed must be posted by users who the user followed or by the user herself.
    Tweets must be ordered from most recent to least recent.
    follow(followerId, followeeId): Follower follows a followee.
    unfollow(followerId, followeeId): Follower unfollows a followee.

Example:
Twitter twitter = new Twitter();

// User 1 posts a new tweet (id = 5).
twitter.postTweet(1, 5);

// User 1's news feed should return a list with 1 tweet id -> [5].
twitter.getNewsFeed(1);

// User 1 follows user 2.
twitter.follow(1, 2);

// User 2 posts a new tweet (id = 6).
twitter.postTweet(2, 6);

// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
twitter.getNewsFeed(1);

// User 1 unfollows user 2.
twitter.unfollow(1, 2);

// User 1's news feed should return a list with 1 tweet id -> [5],
// since user 1 is no longer following user 2.
twitter.getNewsFeed(1);
 */

// This is a Problem about OOD
// You can watch the code and comments...
class Twitter {
    // the global var to store time
    private static int timeStamp = 0;
    // easy to find if user exist
    private HashMap<Integer, User> userMap;

    /** Initialize your data structure here. */
    public Twitter() {
        userMap = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        userMap.get(userId).post(tweetId);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent.
     */
    /**
     * Owing to the tweet_head pointer, we can turn this problem into Question: Merge k Sorted List
     * https://github.com/cherryljr/LintCode/blob/master/Merge%20k%20Sorted%20Lists.java
     * Best part of this.
     * First get all Tweet lists from one user including itself and all people it followed.
     * Second add all heads into a max heap. Every time we poll a tweet with
     * largest time stamp from the heap, then we add its next tweet into the heap.
     * So after adding all heads we only need to add 9 tweets at most into this
     * heap before we get the 10 most recent tweet.
     * @param userId
     * @return
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> rst = new ArrayList<>();
        if (!userMap.containsKey(userId)) {
            return rst;
        }

        Set<Integer> users = userMap.get(userId).getFollowed();
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>(10, (a, b) -> b.time - a.time);
        for (Integer u : users) {
            Tweet t = userMap.get(u).getTweet_head();
            // very imporant! If we add null to the head we are screwed.
            if (t != null) {
                minHeap.add(t);
            }
        }

        int count = 0;
        while (!minHeap.isEmpty() && count < 10) {
            Tweet t = minHeap.poll();
            rst.add(t.id);
            count++;
            if (t.next != null) {
                minHeap.add(t.next);
            }
        }

        return rst;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User user = new User(followerId);
            userMap.put(followerId, user);
        }
        if (!userMap.containsKey(followeeId)) {
            User user = new User(followeeId);
            userMap.put(followeeId, user);
        }
        userMap.get(followerId).follow(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId) || followerId == followeeId) {
            return;
        }
        userMap.get(followerId).unfollow(followeeId);
    }

    /**
     * OO design so User can follow, unfollow and post itself.
     * The property of the class are better to be private.
     */
    class User {
        private int id;
        // Store the Tweet that the user post
        // Think about why we use the pointer but not the arrays or set ?
        // (Similar to Using LinkedList)
        private Tweet tweet_head;
        private Set<Integer> followed;

        public Tweet getTweet_head() {
            return tweet_head;
        }

        public Set<Integer> getFollowed() {
            return followed;
        }

        User(int id) {
            this.id = id;
            followed = new HashSet<>();
            // The user should first follow itself
            // So that when we do getNewsFeed, we get get the result directly.
            follow(id);
            this.tweet_head = null;
        }

        public void follow(int id) {
            followed.add(id);
        }

        public void unfollow(int id) {
            followed.remove(id);
        }

        /**
         * everytime user post a new tweet, add it to the head of Tweet list.
         * @param id - the Tweet id
         */
        public void post(int id) {
            Tweet t = new Tweet(id);
            t.next = tweet_head;
            tweet_head = t;
        }
    }

    /**
     * Tweet link to next Tweet so that we can save a lot of time
     * when we execute getNewsFeed(userId)
     */
    private class Tweet {
        int id;
        int time;
        Tweet next;
        // String content; We don't need this property here...

        Tweet(int id) {
            this.id = id;
            time = timeStamp++;
            next = null;
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */