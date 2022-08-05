import React from "react";
import { Link } from "react-router-dom";
import user from "../images/calender.png";

const TaskCard = (props) => {
  const { id, description, taskDate } = props.task;
  return (
    <div className="item">
      <img className="ui avatar image" src={user} alt="user" />
      <div className="content">
        <Link
          to={{ pathname: `/task/${id}`, state: { task: props.task } }}
        >
          <div className="header">{description}</div>
          <div>{taskDate}</div>
        </Link>
      </div>
      <i
        className="trash alternate outline icon"
        style={{ color: "red", marginTop: "7px", marginLeft: "10px" }}
        onClick={() => props.clickHander(id)}
      ></i>
      <Link to={{ pathname: `/edit`, state: { task: props.task } }}>
        <i
          className="edit alternate outline icon"
          style={{ color: "blue", marginTop: "7px" }}
        ></i>
      </Link>
    </div>
  );
};

export default TaskCard;
