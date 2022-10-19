package com.example.todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TasksDao {
    @Insert
    Long add(Task task);

    @Query("SELECT * FROM tbl_tasks ORDER BY `last_update` DESC")
    List<Task> getAll();

    @Delete
    int delete(Task task);

    @Query("DELETE FROM tbl_tasks")
    void deleteAll();

    @Update
    int update(Task task);

    @Query("SELECT * FROM tbl_tasks WHERE `title` LIKE '%' || :title || '%' ORDER BY `last_update` DESC")
    List<Task> search(String title);
}
