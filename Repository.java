import java.util.*;
import java.text.SimpleDateFormat;

// Evelyn Salas
//
// This class creates a mini git, or a version control system that allows users to create commits
// to track changes made to a document.

public class Repository {
    private Commit front;
    private String name;
    private int size = this.size;

    /**
     * Behavior: Creates a new repo of a given name.
     * Exception: Throws new IllegalArgumentException if name is null.
     * Parameters: String name of the repo.
     */
    public Repository(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        repo = new Repository(name);
    }

    /**
     * Behavior: Returns the ID of the first element in the repo, returns null if
     * first
     * element is null.
     * Returns: String ID of the first element in th repo.
     */
    public String getRepoHead() {
        if (front.id == null) {
            return null;
        }
        return front.id;
    }

    /**
     * Behavior: Returns the number of commits in a repo.
     * Returns: int size of the repo.
     */
    public int getRepoSize() {
        return size;
    }

    /**
     * Behavior: Returns a string representation of the contents in the repo.
     * Returns: String repo name + either " - No commits" if no commits have been
     * made or
     * String repo name + " - Current head: " + first element of the repository.
     */
    public String toString() {
        String head = front.toString();
        if (getRepoSize() == 0) { // no commits
            return name + " - No commits";
        }
        return name + " - Current head: " + head;
    }

    /**
     * Behavior: Returns true if repo contains the given ID.
     * Returns: True if repo contains the given ID, false if it does not.
     * Parameters: String targetId, the id of a commit.
     */
    public boolean contains(String targetId) {
        if (this.contains(targetId)) {
            return true;
        }
        return false;
    }

    /**
     * Behavior: Returns a String representation of the n most recent commits in
     * this repo.
     * Exception: Throws new IllegalArgumentException if n is a non positive number.
     * Returns: String history, a representation of the most recent commits.
     * Parameters: int n, the number of commits we want to return.
     */
    public String getHistory(int n) {
        // if n is non positive, throw exception
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        // if there are no commits
        if (n == 0) {
            return "";
        }
        // if n is less than the number of commits in repo, return all
        String history = " ";
        Commit curr = front;
        if (n < size) {
            while (curr != null) {
                history += curr.id + " at " + curr.timeStamp + " PDT: " + curr.message;
                curr = curr.past;
            }
            return history;
        }
        for (int i = 0; i <= n; i++) {
            while (curr != null) {
                history += curr.id + " at " + curr.timeStamp + " PDT: " + curr.message;
                if (curr.past != null) {
                    history += "\n";
                }
                curr = curr.past;
            }
            return history;
        }
    }

    /**
     * Behavior: Creates a new commit in the repo with a given message.
     * Returns: Returns the ID of the new commit.
     * Parameters: String message we want to commit to the repo.
     */
    public String commit(String message) {
        if (front == null) {
            front = new Commit(message);
        } else {
            Commit curr = front;
            while (curr.past != null) {
                curr = curr.past;
            }
            curr.past = new Commit(message);
        }
        size++;
        return front.id;
    }

    /**
     * Behavior: Removes the commits with the given ID while maintaing the hisroy
     * behind it.
     * Returns: Returns true if the commit was dropped, false if no commit matches
     * the given ID.
     * Parameters: String targetID, the ID of the commit we want to drop.
     */
    public boolean drop(String targetId) {
        if (this.contains(targetId)) {
            // drop commit
            if (front != null) {
                if (front.contains(targetId)) {
                    front = front.past;
                    size--;
                } else {
                    Commit curr = front;
                    while (curr.past != null && !curr.past.contains(targetId)) {
                        curr = curr.past;
                    }
                    if (curr.past != null) {
                        curr.past = curr.past.past;
                        size--;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Behavior: Takes all the commits in other repo and merges it with this repo,
     * preserveing the
     * chronological order.
     * Parameters: Repository other, a second repo.
     */
    public void synchronize(Repository other) {
        Commit curr = front;
        Commit temp = other.front;
        if (other.isEmpty) {
            // remain unchanged
        }
        if (this.isEmpty) {
            // all commits in other should be moved into this
            while (other != null) {
                curr = temp;
                curr = curr.past;
                temp = temp.past;
            }

        } else {
            // front
            if (front == null) {
                front = other.front;
                other.front = null;
            } else {
                // middle
                while (curr.past != null && other.front != null) {
                    Commit commit1 = this.Commit;
                    Commit commit2 = other.Commit;
                    if (commit1.timeStamp < commit2.timeStamp) {
                        temp.past = curr;
                        temp.past.past = curr.past;
                        curr = temp;
                    } else {
                        temp = temp.past;
                        temp.past = curr.past;
                        curr.past = temp;
                        curr = temp.past;
                    }
                }
                // last
                if (curr.past == null) {
                    curr.past = other.front;
                    other.front = null;
                }
            }
        }
    }

    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this
     * class openly mention the fields of the class. This is fine
     * because the fields of the Commit class are public. In general,
     * be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         *
         * @param message A message describing the changes made in this commit.
         * @param past    A reference to the commit made immediately before this
         *                commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         *
         * @param message A message describing the changes made in this commit.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         * "[identifier] at [timestamp]: [message]"
         *
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
         * Resets the IDs of the commit nodes such that they reset to 0.
         * Primarily for testing purposes.
         */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
