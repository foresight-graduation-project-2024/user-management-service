package com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.mapper;

import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TaskCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.collection.TeamCollection;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.dto.CreateTeamRequest;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Member;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Task;
import com.foresight.taskmanagmentservicebackend.taskmanagmentservicebackend.entity.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class MapperClass {
    public static Task taskCollectionToTask(TaskCollection task) {
        if ( task == null ) {
            return null;
        }

        Task task1 = new Task();

        task1.setTaskId( task.getTaskId() );
        task1.setTitle( task.getTitle() );
        task1.setDescription( task.getDescription() );
        task1.setStatus( task.getStatus() );
        task1.setPriority( task.getPriority() );
        task1.setStartDate( task.getStartDate() );
        task1.setEndDate( task.getEndDate() );
        task1.setCreator( task.getCreator() );
        task1.setAssignee( task.getAssignee() );

        return task1;
    }
    public static TeamCollection createTeamRequestToTeamCollection(CreateTeamRequest request) {
        if ( request == null ) {
            return null;
        }

        TeamCollection teamCollection = new TeamCollection();

        teamCollection.setName( request.getName() );
        teamCollection.setDescription( request.getDescription() );
        teamCollection.setTeamLeader( request.getTeamLeader() );
        List<Member> list = request.getMembers();
        if ( list != null ) {
            teamCollection.setMembers( new ArrayList<Member>( list ) );
        }

        return teamCollection;
    }

    public static Team teamCollectionToTeam(TeamCollection teamCollection) {
        if ( teamCollection == null ) {
            return null;
        }

        Team team = new Team();

        team.setTeamId( teamCollection.getTeamId() );
        team.setName( teamCollection.getName() );
        team.setTeamLeader( teamCollection.getTeamLeader() );
        List<Member> list = teamCollection.getMembers();
        if ( list != null ) {
            team.setMembers( new ArrayList<Member>( list ) );
        }
        team.setDescription( teamCollection.getDescription() );

        return team;
    }
}
