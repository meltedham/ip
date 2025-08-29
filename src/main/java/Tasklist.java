import java.util.ArrayList;

public class Tasklist {
    private ArrayList<Task> list;

    public Tasklist(ArrayList<Task> tasks) {
        this.list = tasks;
    }

    public Task getTask(int i) {
        return this.list.get(i);
    }

    public void Mark(int taskNum) {
        list.get(taskNum - 1).Mark();
    }

    public void Unmark(int taskNum) {
        list.get(taskNum - 1).Unmark();
    }

    public void Delete(int taskNum) {
        list.remove(taskNum - 1);
    }

    public void Add(Task task) {
        list.add(task);
    }

    public int taskCount() {
        return this.list.size();
    }

    public void printList() {
        for (int i = 0; i < this.list.size(); i++) {
            System.out.println((i + 1) + ". " + this.list.get(i).toString());
        };
    }

    public void printTask(int taskNum) {
        System.out.println(this.list.get(taskNum - 1).toString());
    }
}
