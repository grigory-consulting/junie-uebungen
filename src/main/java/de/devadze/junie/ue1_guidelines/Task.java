package de.devadze.junie.ue1_guidelines;

import java.time.LocalDate;

public class Task {
	private final int id;
	private String title;
    private boolean done;
    private LocalDate due_date;

    public Task(int id, String title, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.due_date = dueDate;
    }

	public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) { this.title = t; }

    public boolean isDone() {
        return done;
    }

    void setDone(boolean d) {
		done = d;
    }

    public LocalDate due() {
        return due_date;
    }

    @Override
    public String toString() {
        return "Task{" + id + ", " + title + ", done=" + done + ", due=" + due_date + "}";
    }
}
