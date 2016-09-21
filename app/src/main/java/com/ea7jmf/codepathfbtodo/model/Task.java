package com.ea7jmf.codepathfbtodo.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "Tasks")
public class Task extends Model {

    @Column(name = "Name")
    public String name;

    @Column(name = "Priority")
    public int priority;

    @Column(name = "Done")
    public boolean done;

    @Column(name = "CreatedAt")
    public Date createdAt;

    @Column(name = "DueAt")
    public Date dueAt;

    public Task(String name, int priority, boolean done, Date createdAt, Date dueAt) {
        super();
        this.name = name;
        this.priority = priority;
        this.done = done;
        this.createdAt = createdAt;
        this.dueAt = dueAt;
    }

    public Task() {
        super();
    }

    public static List<Task> getAll() {
        return new Select()
                .from(Task.class)
                .orderBy("Id ASC")
                .execute();
    }

    public static Task getById(long id) {
//        return new Select()
//                .from(Task.class)
//                .where("Id = ?")
//                .limit(1)
//                .executeSingle();
        return Task.load(Task.class, id);
    }

    @Override
    public String toString() {
        return name;
    }
}
