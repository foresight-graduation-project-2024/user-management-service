package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.service;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TaskCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TaskSearchCriteria;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.TaskSummary;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Task;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.MapperClass;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper.TaskMapper;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.repo.TaskCollectionRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskMapper mapper;
    private TaskCollectionRepo taskCollectionRepo;
    private UserService userService;
    private TeamService teamService;
    private MongoTemplate mongoTemplate;

    public void createTask(TaskCollection taskCollection, String teamId) {
        TaskCollection task = taskCollectionRepo.save(taskCollection);
        Task task1 = mapper.taskCollectionToTask(task);
        userService.addTask(task1, taskCollection.getAssignee().getMemberId());
        teamService.addTask(task1, teamId);
    }

    public void editTask(TaskCollection taskCollection, String teamId) {
        TaskCollection taskCollection1 = taskCollectionRepo.findById(taskCollection.getTaskId()).orElseThrow(() -> new RuntimeException("task is not found"));
        BeanUtils.copyProperties(taskCollection, taskCollection1, "taskId");
        taskCollectionRepo.save(taskCollection1);
        Task task = mapper.taskCollectionToTask(taskCollection1);
        userService.editTask(task);
        teamService.editTask(task, teamId);
    }

    public TaskCollection getTask(String id) {
        return taskCollectionRepo.findById(id).orElseThrow(() -> new RuntimeException("task is not found"));
    }

    public Page<TaskSummary> getSummaries(Pageable pageable) {
        List<TaskSummary> summaries = mongoTemplate.find(new Query().with(pageable), TaskSummary.class, "task");
        long count = mongoTemplate.count(new Query().with(pageable), TaskSummary.class, "task");
        return new PageImpl<>(summaries, pageable, count);
    }

    public Page<TaskSummary> searchSummaries(Pageable pageable, TaskSearchCriteria criteria) {
        Query query = new Query();
        if (criteria.getTitle() != null || !criteria.getTitle().isEmpty()) {
            query.addCriteria(Criteria.where("title").is(criteria.getTitle()));
        }
        if (criteria.getStatus() != null) {
            query.addCriteria(Criteria.where("status").is(criteria.getStatus()));
        }
        if (criteria.getPriority() != null) {
            query.addCriteria(Criteria.where("priority").is(criteria.getPriority()));
        }
        if (criteria.getStartDate() != null) {
            query.addCriteria(Criteria.where("startDate").is(criteria.getStartDate()));
        }
        if (criteria.getEndDate() != null) {
            query.addCriteria(Criteria.where("endDate").is(criteria.getEndDate()));
        }
        if (criteria.getCreator() != null) {
            query.addCriteria(Criteria.where("creator").is(criteria.getCreator()));
        }
        if (criteria.getAssignee() != null) {
            query.addCriteria(Criteria.where("assignee").is(criteria.getAssignee()));
        }
        List<TaskSummary> summaries = mongoTemplate.find(query.with(pageable), TaskSummary.class, "task");
        long count = mongoTemplate.count(new Query().with(pageable), TaskSummary.class, "task");
        return new PageImpl<>(summaries, pageable, count);

    }

    public void deleteTask(String teamId, String taskId) {
        TaskCollection taskCollection= taskCollectionRepo.findById(taskId).orElseThrow(()->new RuntimeException("task is not found"));
        userService.deleteTask(taskCollection.getAssignee().getMemberId(),taskId);
        teamService.deleteTeamTask(teamId,taskId);
        taskCollectionRepo.deleteById(taskId);
    }
}
