import React from "react";
import { Link } from "react-router-dom";
import TaskCard from "./TaskCard";

const TaskList = (props) => {
  console.log(props);

  const deleteTaskHandler = (id) => {
    props.getTaskId(id);
  };

  const renderTaskList = props.tasks.map((task) => {
    return (
      <TaskCard
        task={task}
        clickHander={deleteTaskHandler}
        key={task.id}
      />
    );
  });
  return (
    <div className="main">
      <h2>
        <Link to={{ pathname: '/Add' }} >
          <button className="ui button blue right">New</button>
        </Link> 
      </h2>
      <br/>
      <br/>
      <div className="ui celled list">{renderTaskList}</div>
    </div>
  );
};

export default TaskList;
