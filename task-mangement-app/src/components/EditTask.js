import React from "react";

class EditTask extends React.Component {
  constructor(props) {
    super(props);
    const { id, description, taskDate } = props.location.state.task;
    this.state = {
      id,
      description,
      taskDate,
    };
  }

  update = (e) => {
    e.preventDefault();
    if (this.state.description === "" || this.state.taskDate === "") {
      alert("ALl the fields are mandatory!");
      return;
    }
    this.props.updateTaskHandler(this.state);
    this.setState({ description: "", taskDate: "" });
    this.props.history.push("/");
  };
  render() {
    return (
      <div className="ui main">
        <h2>Edit Task</h2>
        <form className="ui form" onSubmit={this.update}>
          <div className="field">
            <label>Description</label>
            <input
              type="text"
              name="description"
              placeholder="Description"
              value={this.state.description}
              onChange={(e) => this.setState({ description: e.target.value })}
            />
          </div>
          <div className="field">
            <label>Date</label>
            <input
              type="text"
              name="taskDate"
              placeholder="Date"
              value={this.state.taskDate}
              onChange={(e) => this.setState({ taskDate: e.target.value })}
            />
          </div>
          <button className="ui button blue">Update</button>
        </form>
      </div>
    );
  }
}

export default EditTask;
