import React from "react";
import { Link } from "react-router-dom";
import user from "../images/user.jpg";

const TaskDetail = (props) => {
  const { description, taskDate } = props.location.state.task;
  return (
    <div className="main">
      <div className="ui card centered">
        <div className="image">
          <img src={user} alt="user" />
        </div>
        <div className="content">
          <div className="header">Task Detail</div>
          <div className="description">{description}</div>
          <div className="description">{taskDate}</div>
        </div>
      </div>
      <div className="center-div">
        <Link to="/">
          <button className="ui button blue center">
            Back to Task List
          </button>
        </Link>
      </div>
    </div>
  );
};

export default TaskDetail;
