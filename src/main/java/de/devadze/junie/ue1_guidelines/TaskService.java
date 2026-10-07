package de.devadze.junie.ue1_guidelines;

import java.time.LocalDate;
import java.util.*;

public class TaskService {

	private final List<Task> TASKS = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String title, LocalDate due) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title");
        Task t = new Task(nextId++, title.trim(), due);
        TASKS.add(t);
        return t;
    }

	public List<Task> list_all() {
		return TASKS;
	}

    public Task find_by_id(int ID) {
        for (Task t : TASKS) {
            if (t.getId() == ID) return t;
        }
        return null;
    }

    public Task Toggle(int id) {
		Task t = find_by_id(id);
		if (t == null) {
			return null;
		}
		t.setDone(!t.isDone());
		return t;
    }

    public boolean remove(int id) {
        Iterator<Task> it = TASKS.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) { it.remove(); return true; }
        }
        return false;
    }
}
