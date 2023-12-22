package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.controller;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TaskCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TaskSearchCriteria;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TaskSummary;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;
    @PostMapping("/{teamId}")
    public void addTask(@RequestBody TaskCollection taskCollection,@PathVariable String teamId){
        taskService.createTask(taskCollection,teamId);
    }
    @PutMapping("/{teamId}")
    public void editTask(@RequestBody TaskCollection taskCollection,@PathVariable String teamId){
        taskService.editTask(taskCollection,teamId);
    }
    @GetMapping("/{id}")
    public TaskCollection getTask(@PathVariable String id){
        return taskService.getTask(id);
    }
    @GetMapping
    public Page<TaskSummary> getTasksSummary(Pageable pageable){

        return taskService.getSummaries(pageable);
    }
    @GetMapping("/search")
    public Page<TaskSummary> searchInTasks(Pageable pageable, TaskSearchCriteria criteria){

         return taskService.searchSummaries(pageable,criteria);
    }

    @DeleteMapping("/{teamId}/{taskId}")
    public void deleteTask(@PathVariable String teamId,@PathVariable String taskId){
       taskService.deleteTask(teamId,taskId);
    }

}
