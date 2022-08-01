import React from "react";

class AddTask extends React.Component {
  state = {
    description: "",
    taskDate: "",
  };

  add = (e) => {
    e.preventDefault();
    if (this.state.description === "" || this.state.taskDate === "") {
      alert("ALl the fields are mandatory!");
      return;
    }
    this.props.addTaskHandler(this.state);
    this.setState({ description: "", taskDate: "" });
    this.props.history.push("/");
  };
  render() {
    return (
      <div className="ui main">
        <h2>Add Task</h2>
        <form className="ui form" onSubmit={this.add}>
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
              placeholder="taskDate"
              value={this.state.taskDate}
              onChange={(e) => this.setState({ taskDate: e.target.value })}
            />
          </div>
          <button className="ui button blue">Add</button>
        </form>
      </div>
    );
  }
}

export default AddTask;
